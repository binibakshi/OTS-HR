package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.*;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Repository.MossadotRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;
import teachers.biniProject.Repository.TeacherReformsRepository;

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
    private TeacherReformsRepository teacherReformsRepository;

    public List<TeacherEmploymentDetails> findAll() {
        return teacherEmploymentDetailsRepository.findAll();
    }

    public void deleteByEmpId(String empId) {
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
        String empId = "";
        int mossadId = 0, reformType = 0;
        double oldHours = 0, newHours = 0;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();

        // check validation raise exception if needed
        this.checkNewHoursValidation(teacherEmploymentDetails);

        empId = teacherEmploymentDetails.get(0).getEmpId();
        mossadId = teacherEmploymentDetails.get(0).getMossadId();
        reformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());

        //  set some values
        this.setRecordForSave(teacherEmploymentDetails);

        // remove unnecessary items
        teacherEmploymentDetails = teacherEmploymentDetails.stream().filter(el -> el.getHours() != 0).collect(Collectors.toList());

        oldHours = this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadIdAndReformType(empId, mossadId, reformType)
                .stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        if (!teacherEmploymentDetails.isEmpty()) {
            newHours = teacherEmploymentDetails.stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                    .mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }

        this.teacherEmploymentDetailsRepository.deleteByEmpIdAndMossadIdAndReformType(empId, mossadId, reformType);
        this.teacherEmploymentDetailsRepository.saveAll(teacherEmploymentDetails);
        this.updateMossadHours(mossadId, (int) (newHours - oldHours));

        return teacherEmploymentDetails;
    }

    // validate checks raise exceptions if needed
    public void checkNewHoursValidation(List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        int currReformType;
        String empId;
        Employee employee;

        if (teacherEmploymentDetails.isEmpty()) {
            throw new GenericException("לא נמצאו נתונים");
        }

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

        List<convertHours> localConvertHours = this.convertHoursService.findAll();

        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

        for (TeacherEmploymentDetails el : teacherEmploymentDetails) {
            el.setBegda(todayDate);
            el.setEndda(cal.getTime());
            el.setReformType(localConvertHours.stream()
                    .filter(e -> e.getCode() == el.getEmpCode()).findFirst().get().getReformType());
        }
    }

    private void getWeeklyHours(float[] week, String empId, int currReformType) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get all exist hours (without this current reform, hours to avoid duplication)
        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId))
            if (!relevantCodes.contains(el.getEmpCode())) {
                week[el.getDay()] += el.getHours();
            }

    }

    public float[] getWeek(String empId) {
        float[] week = new float[7];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndDay(empId, i).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        week[6] = this.getEmpJobPercent(empId);
        return week;
    }

    public float[] getWeekPerMossad(String empId, int mossadId) {
        float[] week = new float[7];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadIdAndDay(empId, mossadId, i).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        week[6] = this.getEmpJobPercentPerMossad(empId, mossadId);
        return week;
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

    public List<TeacherEmploymentDetails> getEmpHoursByMossad(String empId, int mossadId) {
        return this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId);
    }

    public List<TeacherEmploymentDetails> getAllByMossad(int mossadId) {
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();
        return this.teacherEmploymentDetailsRepository
                .findByMossadId(mossadId).stream()
                .filter(el -> frontalCodes.contains(el.getEmpCode()))
                .collect(Collectors.toList());
    }

    // check for max job percent
    private void checkMaxJobPercent(List<TeacherEmploymentDetails> employmentDetails, String empId) {
        int currReformType;
        float maxJobPercet = 0;
        float frontalHours = 0;
        calcHours calcHours;

        maxJobPercet = this.maxJobPercentById(empId);
        currReformType = this.convertHoursService.findByCode(employmentDetails.get(0).getEmpCode());
        List<Integer> relevantCodes = this.convertHoursService.getAllFrontalAndReform(currReformType);

        // get the current frontal hours of the sent reform type (always send from the client for each change)
        frontalHours += employmentDetails.stream().
                filter(el -> relevantCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        //TODO remove or find more appropriate check
        if (frontalHours > 31) {
            throw new GenericException("אי אפשר להזין לעובד כמות שעות כזו");
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

    private void checkHoursMatch(List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId,
                                 boolean isMother, int ageHours, int currReformType) {
        float frontalHours, privateHours, pauseHours;
        calcHours calcHours;

        // get the hours by type(frontal = 1/private = 2/pause = 3)
        List<Integer> frontalCodes = this.convertHoursService.getHoursByType(1);
        List<Integer> privateCodes = this.convertHoursService.getHoursByType(2);
        List<Integer> pauseCodes = this.convertHoursService.getHoursByType(3);

        frontalHours = (float) teacherEmploymentDetails.stream().
                filter(el -> frontalCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        double sum = 0.0;
        for (TeacherEmploymentDetails employmentDetail : teacherEmploymentDetails) {
            if (privateCodes.contains(employmentDetail.getEmpCode())) {
                double hours = employmentDetail.getHours();
                sum += hours;
            }
        }
        privateHours = (float) sum;
        pauseHours = (float) teacherEmploymentDetails.stream().
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

    public float getEmpJobPercent(String empId) {
        final float[] jobPercentSum = {0};
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();

        HashMap<Integer, Float> frontalsum = new HashMap<Integer, Float>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId)) {
            if (frontalCodes.contains(el.getEmpCode())) {
                if (!frontalsum.containsKey(el.getReformType())) {
                    frontalsum.put(el.getReformType(), el.getHours());
                } else {
                    frontalsum.put(el.getReformType(), el.getHours() + frontalsum.get(el.getReformType()));
                }
            }
        }
        frontalsum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getByFrontalTzReformType(empId, v, k).getJobPercent()
        );
        return jobPercentSum[0];
    }

    public float getEmpJobPercentPerMossad(String empId, int mossadId) {
        final float[] jobPercentSum = {0};
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();

        HashMap<Integer, Float> frontalsum = new HashMap<Integer, Float>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId)) {
            if (frontalCodes.contains(el.getEmpCode())) {
                if (!frontalsum.containsKey(el.getReformType())) {
                    frontalsum.put(el.getReformType(), el.getHours());
                } else {
                    frontalsum.put(el.getReformType(), el.getHours() + frontalsum.get(el.getReformType()));
                }
            }
        }
        frontalsum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getByFrontalTzReformType(empId, v, k).getJobPercent()
        );
        return jobPercentSum[0];
    }

    private float maxJobPercentById(String tz) {
        List<Integer> cuReforms = new ArrayList<>();
        for (TeachersReforms el : this.teacherReformsRepository.findAll()) {
            if (el.getEmpId().equals(tz)) {
                Integer reformType = el.getReformType();
                cuReforms.add(reformType);
            }
        }
        if (cuReforms.contains(2) || cuReforms.contains(3) || cuReforms.contains(4)) return 117;
        else if (cuReforms.contains(5) || cuReforms.contains(6)) return 125;
        else if (cuReforms.contains(1) || cuReforms.contains(7)) return 140;
        else return 100;
    }
}
