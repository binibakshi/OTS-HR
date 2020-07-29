package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.Mossadot;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Entity.calcHours;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Repository.MossadotRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TeacherEmploymentDetailsService {

    private final float MAX_HOURS_PER_DAY = 9;
    private final float MAX_HOURS_FRIDAY = 6;

    @Autowired
    private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ConvertHoursService convertHoursService;

    @Autowired
    private CalcHoursService calcHourService;

    @Autowired
    private MossadotRepository mossadotRepository;

    @Autowired
    private helperService helperService;

    public List<TeacherEmploymentDetails> findAll() {
        return teacherEmploymentDetailsRepository.findAll();
    }

    public TeacherEmploymentDetails findById(int id) {
        return teacherEmploymentDetailsRepository.findById(id).orElse(null);
    }

    void deleteByEmpId(String empId) {
        this.teacherEmploymentDetailsRepository.deleteByEmpId(empId);
    }

    public void deleteByEmpIdAndMossadId(String empId, int mossadId) {
        this.teacherEmploymentDetailsRepository.deleteByEmpIdAndMossadId(empId, mossadId);
    }

    public TeacherEmploymentDetails save(TeacherEmploymentDetails teacherEmploymentDetails) {
        Calendar calendar = Calendar.getInstance();
        teacherEmploymentDetails.setBegda(calendar.getTime());
        calendar.set(Calendar.getInstance().get(Calendar.YEAR + 1), Calendar.DECEMBER, 31);
        teacherEmploymentDetails.setEndda(calendar.getTime());
        return teacherEmploymentDetailsRepository.save(teacherEmploymentDetails);
    }

    public List<TeacherEmploymentDetails> saveAll(List<TeacherEmploymentDetails> teacherEmploymentDetails) {

        // check validation raise exception if needed
        this.checkNewHoursValidation(teacherEmploymentDetails);

        this.setRecordForSave(teacherEmploymentDetails);
        teacherEmploymentDetails.forEach(el -> {
            // check if the record not exist yet
            if (this.updateIfExist(el) != true) {
                if (el.getHours() != 0) {
                    this.updateMossadHours(el.getMossadId(), (int) el.getHours());
                    teacherEmploymentDetailsRepository.save(el);
                }
            }
        });
        return teacherEmploymentDetails;
    }

    // validate checks raise exceptions if needed
    public void checkNewHoursValidation(List<TeacherEmploymentDetails> teacherEmploymentDetails) {

        int currReformType;
        String empId;
        Employee employee;

        // get the current reform type
        currReformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());

        empId = teacherEmploymentDetails.get(0).getEmpId();
        employee = this.employeeService.findById(empId);
        this.checkHoursMatch(teacherEmploymentDetails, empId, employee.isMother(),
                employeeService.getAgeHours(employee.getBirthDate()), currReformType);

        // TODO when Authorization is up check if administration skip those checks
        this.checkMaxHoursInDay(teacherEmploymentDetails, empId, currReformType);
        this.checkMaxJobPercent(teacherEmploymentDetails, empId);
        this.checkMaxHoursPerMossad(teacherEmploymentDetails);
    }

    private void checkMaxHoursInDay(List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId, int currReformType) {
        float[] week = new float[6];
        Arrays.fill(week, 0);

        this.getWeeklyHours(week, empId, currReformType);

        // TODO Auto-generated method stub
        IntStream.range(0, teacherEmploymentDetails.size()).forEach(i -> {
            week[teacherEmploymentDetails.get(i).getDay()] += teacherEmploymentDetails.get(i).getHours();
            if (week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_PER_DAY) {
                throw new GenericException("אי אפשר להזין יותר מ" + MAX_HOURS_PER_DAY + " שעות ביום" + (teacherEmploymentDetails.get(i).getDay() + 1));
            } else if (teacherEmploymentDetails.get(i).getDay() == 5)
                if (week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_FRIDAY) {
                    throw new GenericException("אי אפשר להזין יותר מ" + MAX_HOURS_FRIDAY + " שעות ביום" + (teacherEmploymentDetails.get(i).getDay() + 1));
                }
        });
    }

    // Set some values
    public void setRecordForSave(List<TeacherEmploymentDetails> teacherEmploymentDetails) {

        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

        for (TeacherEmploymentDetails el : teacherEmploymentDetails) {
            el.setBegda(todayDate);
            el.setEndda(cal.getTime());
        }
    }

    private void getWeeklyHours(float[] week, String empId, int currReformType) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get all exist hours (without this current reform, hours to avoid duplication)
        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId))
            if (relevantCodes.contains(el.getEmpCode())) {
                week[el.getDay()] += el.getHours();
            }

    }

    public float[] getWeek(String empId) {
        float[] week = new float[6];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndDay(empId, i).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        return week;
    }

    public float[] getWeekPerMossad(String empId, int mossadId) {
        float[] week = new float[6];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadIdAndDay(empId, mossadId, i).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        return week;
    }

    private boolean updateIfExist(TeacherEmploymentDetails teacherEmploymentDetails) {
        double hoursToMossad = 0;

        TeacherEmploymentDetails temp;
        temp = this.teacherEmploymentDetailsRepository.findSingleRecord(teacherEmploymentDetails.getEmpId(),
                teacherEmploymentDetails.getDay(),
                teacherEmploymentDetails.getMossadId(),
                teacherEmploymentDetails.getEmpCode());

        // check if there is old
        if (temp == null) {
            return false;
        }

        // check if there is change at all
        if (temp.getHours() == teacherEmploymentDetails.getHours()) {
            return true;
        }
        hoursToMossad = teacherEmploymentDetails.getHours() - temp.getHours();
        this.updateMossadHours(teacherEmploymentDetails.getMossadId(), (int) hoursToMossad);

        // set new hours to update
        temp.setHours(teacherEmploymentDetails.getHours());

        // delete if there is no hours after changes
        if (temp.getHours() == 0) {
            this.teacherEmploymentDetailsRepository.delete(temp);
        } else {
            this.teacherEmploymentDetailsRepository.save(temp);
        }
        return true;
    }

    public List<TeacherEmploymentDetails> getAllByReformType(String empId, int mossadId, int reformType) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(reformType);
        List<TeacherEmploymentDetails> list = new ArrayList<>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId)) {
            if (relevantCodes.contains(el.getEmpCode())) {
                list.add(el);
            }
        }
        return list;
    }

    // check for max job percent
    private void checkMaxJobPercent(List<TeacherEmploymentDetails> employmentDetails, String empId) {
        int currReformType;
        float maxJobPercet = 0;
        float frontalHours = 0;
        calcHours calcHours;

        maxJobPercet = helperService.maxJobPercentById(empId);
        currReformType = this.convertHoursService.findByCode(employmentDetails.get(0).getEmpCode());
        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get the current codes (to not loop at them...)
        final List<Integer> currCodes = employmentDetails.stream().
                filter(el -> relevantCodes.contains(el.getEmpCode())).
                map(TeacherEmploymentDetails::getEmpCode).distinct().
                collect(Collectors.toList());
        // get the current frontal hours of the sent reform type (always send from the client for each change)
        frontalHours += employmentDetails.stream().
                filter(el -> relevantCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();
        // get the other exist codes
        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId)) {
            if (relevantCodes.contains(el.getEmpCode()) &&
                    !currCodes.contains(el.getEmpCode())) {
                double hours = el.getHours();
                frontalHours += hours;
            }
        }
        calcHours = this.calcHourService.getByFrontalTzReformType(empId, frontalHours, currReformType);

        if (calcHours.getJobPercent() > maxJobPercet) {
            throw new GenericException("לעובד יש מגבלת מגבלת אחוז משרה של " + maxJobPercet + "כעת יש %" + calcHours.getJobPercent() + "%");
        }
    }

    //  check if there no exception from max hours per mossad
    private void checkMaxHoursPerMossad(List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        double newHours = 0;
        double currHoursPerMossad = 0;
        double maxHoursPerMossad = 0;

        newHours = teacherEmploymentDetails.stream().
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();
        currHoursPerMossad = mossadotRepository.findById(teacherEmploymentDetails.get(0).getMossadId()
        ).get().getCurrHours();
        maxHoursPerMossad = mossadotRepository.findById(teacherEmploymentDetails.get(0).getMossadId()
        ).get().getMaxHours();

        if ((newHours + currHoursPerMossad) > maxHoursPerMossad) {
            throw new GenericException("למוסד יש מגבלת של" + maxHoursPerMossad + "שעות");
        }
    }

    private void checkHoursMatch(List<TeacherEmploymentDetails> employmentDetails, String empId,
                                 boolean isMother, int ageHours, int currReformType) {
        float frontalHours, privateHours, pauseHours;
        calcHours calcHours;

        // get the hours by type(frontal = 1/private = 2/pause = 3)
        List<Integer> frontalCodes = this.convertHoursService.getHoursByType(1);
        List<Integer> privateCodes = this.convertHoursService.getHoursByType(2);
        List<Integer> pauseCodes = this.convertHoursService.getHoursByType(3);

        frontalHours = (float) employmentDetails.stream().
                filter(el -> frontalCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        double sum = 0.0;
        for (TeacherEmploymentDetails employmentDetail : employmentDetails) {
            if (privateCodes.contains(employmentDetail.getEmpCode())) {
                double hours = employmentDetail.getHours();
                sum += hours;
            }
        }
        privateHours = (float) sum;
        pauseHours = (float) employmentDetails.stream().
                filter(el -> pauseCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        calcHours = this.calcHourService.getByFrontalHours(currReformType, isMother, ageHours, frontalHours);

        if (calcHours == null) {
            throw new GenericException("לא קיים פיצול שעות עבור נתונים שהוזנו");
        }
        if (calcHours.getPauseHours() != pauseHours) {
            throw new GenericException("אי תאימות בשעות נא להזין " + calcHours.getPauseHours() + "שעות שהייה");
        } else if (calcHours.getPrivateHours() != privateHours) {
            throw new GenericException("אי תאימות בשעות נא להזין " + calcHours.getPrivateHours() + "שעות פרטניות");
        }
    }

    // add or sub the total hours
    private void updateMossadHours(int mossadId, int hourToAdd) {
        // set the new hours in total sum hours per mossad
        Mossadot mossad = new Mossadot(this.mossadotRepository.findById(mossadId).get());
        mossad.setCurrHours(mossad.getCurrHours() + hourToAdd);
        this.mossadotRepository.save(mossad);
    }
}
