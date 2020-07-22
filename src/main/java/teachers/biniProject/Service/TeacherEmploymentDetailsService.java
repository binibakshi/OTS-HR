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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteByEmpId(String empId) {
        this.teacherEmploymentDetailsRepository.deleteByEmpId(empId);
    }

    public void deleteByEmpIdAndMossadId(String empId, int mossadId) {
        this.teacherEmploymentDetailsRepository.deleteByEmpIdAndMossad(empId, mossadId);
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
                    this.updateMossadHours(el.getMosadId(), (int) el.getHours());
                    teacherEmploymentDetailsRepository.save(el);
                }
            }
        });
        return teacherEmploymentDetails;
    }

    // validate checks raise exceptions if needed
    public void checkNewHoursValidation(List<TeacherEmploymentDetails> teacherEmploymentDetails) {

        int currReformType;
        String tz;
        Employee employee;

        if (teacherEmploymentDetails.isEmpty()) {
            // ????????????
            return;
        }

        // get the current reform type
        currReformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());

        tz = teacherEmploymentDetails.get(0).getEmpId();
        employee = this.employeeService.findById(tz);
        this.checkHoursMatch(teacherEmploymentDetails, tz, employee.isMother(),
                employeeService.getAgeHours(employee.getBirthDate()), currReformType);

        // TODO when Authorization is up check if administration skip those checks
        this.checkMaxHoursInDay(teacherEmploymentDetails, tz, currReformType);
        this.checkMaxJobPercent(teacherEmploymentDetails, tz);
        this.checkMaxHoursPerMossad(teacherEmploymentDetails);

    }

    private void checkMaxHoursInDay(List<TeacherEmploymentDetails> teacherEmploymentDetails, String tz, int currReformType) {
        float[] week = new float[6];
        for (int j = 0; j < week.length; j++) {
            week[j] = 0;
        }

        this.getWeeklyHours(week, tz, currReformType);

        // TODO Auto-generated method stub
        for (int i = 0; i < teacherEmploymentDetails.size(); i++) {

            week[teacherEmploymentDetails.get(i).getDay()] += teacherEmploymentDetails.get(i).getHours();

            if (week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_PER_DAY ||
                    (teacherEmploymentDetails.get(i).getDay() == 5 &&
                            week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_FRIDAY)
                    ) {
                throw new GenericException("אי אפשר להזין יותר מ" + MAX_HOURS_PER_DAY + " שעות ביום" + (i + 1));
                //				return false;
            }

        }
    }

    public void setRecordForSave(List<TeacherEmploymentDetails> teacherEmploymentDetails) {


        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

        teacherEmploymentDetails.forEach(el -> {
            el.setBegda(todayDate);
            el.setEndda(cal.getTime());
        });
    }

    public void getWeeklyHours(float[] week, String tz, int currReformType) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get all exist hours (without this current reform, hours to avoid duplication)
        this.teacherEmploymentDetailsRepository.findAll().stream().
                filter(el -> el.getEmpId().equals(tz) &&
                        relevantCodes.contains(el.getEmpCode()) == false).
                forEach(el -> {
                    week[el.getDay()] += el.getHours();
                });
    }

    public List<TeacherEmploymentDetails> getEmpDay(String tz, int day) {
        return this.teacherEmploymentDetailsRepository.findAll().stream().
                filter(i -> i.getEmpId().equals(tz) &&
                        i.getDay() == day).collect(Collectors.toList());
    }

    public float getExistHours(String tz) {
        return (float) this.teacherEmploymentDetailsRepository.findAll().stream().
                filter(i -> i.getEmpId().equals(tz)).mapToDouble(i -> i.getHours()).sum();
    }

    public float[] getWeek(String tz) {
        float[] week = new float[6];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.getEmpDay(tz, i).stream().mapToDouble(el -> el.getHours()).sum();
        }
        return week;
    }

    public boolean updateIfExist(TeacherEmploymentDetails teacherEmploymentDetails) {
        double hoursToMossad = 0;
        Mossadot mossad;

        TeacherEmploymentDetails temp = new TeacherEmploymentDetails(teacherEmploymentDetailsRepository.findAll().stream().
                filter(i -> i.getEmpId().equals(teacherEmploymentDetails.getEmpId()) &&
                        i.getMosadId() == teacherEmploymentDetails.getMosadId() &&
                        i.getDay() == teacherEmploymentDetails.getDay() &&
                        i.getEmpCode() == teacherEmploymentDetails.getEmpCode()).findFirst().orElse(null));


        // cehck if there is old
        if (temp.getEmpId() == null) {
            return false;
        }

        // check if there is change at all
        if (temp.getHours() == teacherEmploymentDetails.getHours()) {
            return true;
        }
        hoursToMossad = teacherEmploymentDetails.getHours() - temp.getHours();
        this.updateMossadHours(teacherEmploymentDetails.getMosadId(), (int) hoursToMossad);

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

    public List<TeacherEmploymentDetails> getAllByReformType(String tz, int mosadId, int reformType) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(reformType);

        return this.teacherEmploymentDetailsRepository.findAll().
                stream().
                filter(i -> i.getEmpId().equals(tz) &&
                        i.getMosadId() == mosadId &&
                        relevantCodes.contains(i.getEmpCode())
                ).
                collect(Collectors.toList());

    }

    // check for max job percent
    private void checkMaxJobPercent(List<TeacherEmploymentDetails> employmentDetails, String tz) {
        int currReformType;
        float maxJobPercet = 0;
        float frontalHours = 0;
        calcHours calcHours;

        if (employmentDetails.size() <= 0) {
            return;
        }

        maxJobPercet = helperService.maxJobPercentById(tz);

        currReformType = this.convertHoursService.findByCode(employmentDetails.get(0).getEmpCode());

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get the current codes (to not loop at them...)
        final List<Integer> currCodes = employmentDetails.stream().
                filter(el -> relevantCodes.contains(el.getEmpCode()) == true).
                map(i -> i.getEmpCode()).distinct().
                collect(Collectors.toList());

        // get the current frontal hours of the sent reform type (always send from the client for each change)
        frontalHours += employmentDetails.stream().
                filter(el -> relevantCodes.contains(el.getEmpCode()) == true).
                mapToDouble(i -> i.getHours()).
                sum();

        // get the other exist codes
        frontalHours += this.teacherEmploymentDetailsRepository.findAll().
                stream().filter(el -> el.getEmpId().equals(tz) &&
                relevantCodes.contains(el.getEmpCode()) == true &&
                currCodes.contains(el.getEmpCode()) == false).
                mapToDouble(i -> i.getHours()).
                sum();

        calcHours = this.calcHourService.getByFrontalTzReformType(tz, frontalHours, currReformType);

        if (calcHours.getJobPercent() > maxJobPercet) {
            throw new GenericException("לעובד יש מגבלת מגבלת אחוז משרה של " + maxJobPercet + "כעת יש %" + calcHours.getJobPercent() + "%");
        }

    }

    //  check if there no exeption from max hours per mossad
    private void checkMaxHoursPerMossad(List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        double newHours = 0;
        double currHoursPerMossad = 0;
        double maxHoursPerMossad = 0;

        newHours = teacherEmploymentDetails.stream().
                mapToDouble(el -> el.getHours()).
                sum();

        currHoursPerMossad = mossadotRepository.findById(teacherEmploymentDetails.get(0).getMosadId()
        ).get().getCurrHours();

        maxHoursPerMossad = mossadotRepository.findById(teacherEmploymentDetails.get(0).getMosadId()
        ).get().getMaxHours();

        if ((newHours + currHoursPerMossad) > maxHoursPerMossad) {
            throw new GenericException("למוסד יש מגבלת של" + maxHoursPerMossad + "שעות");
        }
    }

    private void checkHoursMatch(List<TeacherEmploymentDetails> employmentDetails, String tz,
                                 boolean isMother, int ageHours, int currReformType) {
        float frontalHours, privateHours, pauseHours;
        calcHours calcHours;

        // get the hours by type(frontal = 1/private = 2/pause = 3)
        List<Integer> frontalCodes = this.convertHoursService.getHoursByType(1);
        List<Integer> privateCodes = this.convertHoursService.getHoursByType(2);
        List<Integer> pauseCodes = this.convertHoursService.getHoursByType(3);


        frontalHours = (float) employmentDetails.stream().
                filter(el -> frontalCodes.contains(el.getEmpCode())).
                mapToDouble(i -> i.getHours()).
                sum();

        privateHours = (float) employmentDetails.stream().
                filter(el -> privateCodes.contains(el.getEmpCode())).
                mapToDouble(i -> i.getHours()).
                sum();

        pauseHours = (float) employmentDetails.stream().
                filter(el -> pauseCodes.contains(el.getEmpCode())).
                mapToDouble(i -> i.getHours()).
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
        Mossadot mossad;
        // set the new hours in total sum hours per mossad
        mossad = new Mossadot(this.mossadotRepository.findById(mossadId).get());
        mossad.setCurrHours(mossad.getCurrHours() + hourToAdd);
        this.mossadotRepository.save(mossad);
    }

}
