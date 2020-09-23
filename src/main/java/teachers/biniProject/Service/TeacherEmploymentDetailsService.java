package teachers.biniProject.Service;

import org.jetbrains.annotations.NotNull;
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

    public void deleteByEmpIdAndMossadId(String empId, int mossadId, Date begda, Date endda) {
        this.teacherEmploymentDetailsRepository.deleteByEmpIdAndMossadId(empId, mossadId, begda, endda);
    }

//    public TeacherEmploymentDetails save(@NotNull TeacherEmploymentDetails teacherEmploymentDetails) {
//        Calendar calendar = Calendar.getInstance();
//        teacherEmploymentDetails.setBegda(calendar.getTime());
//        calendar.set(Calendar.getInstance().get(Calendar.YEAR + 1), Calendar.DECEMBER, 31);
//        teacherEmploymentDetails.setEndda(calendar.getTime());
//        return teacherEmploymentDetailsRepository.save(teacherEmploymentDetails);
//    }

    public List<TeacherEmploymentDetails> saveAll(List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        String empId = "";
        int mossadId = 0, reformType = 0;
        Date begda = new Date(), endda = new Date();
        double oldHours = 0, newHours = 0;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();

        // check validation raise exception if needed
        this.checkNewHoursValidation(teacherEmploymentDetails, begda, endda);

        //  set some values
        this.setRecordForSave(teacherEmploymentDetails);
        empId = teacherEmploymentDetails.get(0).getEmpId();
        mossadId = teacherEmploymentDetails.get(0).getMossadId();
        reformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());
        begda = teacherEmploymentDetails.get(0).getBegda();
        endda = teacherEmploymentDetails.get(0).getEndda();

        // remove unnecessary items
        teacherEmploymentDetails = teacherEmploymentDetails.stream().filter(el -> el.getHours() != 0).collect(Collectors.toList());

        oldHours = this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadIdAndReformType(empId, mossadId, reformType, begda, endda)
                .stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                .mapToDouble(TeacherEmploymentDetails::getHours).sum();

        if (!teacherEmploymentDetails.isEmpty()) {
            newHours = teacherEmploymentDetails.stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                    .mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }

        this.teacherEmploymentDetailsRepository.deleteByEmpIdAndMossadIdAndReformType(empId, mossadId, reformType, begda, endda);
        this.teacherEmploymentDetailsRepository.saveAll(teacherEmploymentDetails);
        this.updateMossadHours(mossadId, (int) (newHours - oldHours));

        return teacherEmploymentDetails;
    }

    // validate checks raise exceptions if needed
    public void checkNewHoursValidation(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails, Date begda, Date endda) {
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
        this.checkMaxHoursInDay(teacherEmploymentDetails, empId, currReformType, begda, endda);
        this.checkMaxJobPercent(teacherEmploymentDetails, empId);
        this.checkMaxHoursPerMossad(teacherEmploymentDetails);
    }

    private void checkMaxHoursInDay(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId, int currReformType, Date begda, Date endda) {
        float[] week = new float[6];
        Arrays.fill(week, 0);

        this.getWeeklyHours(week, empId, teacherEmploymentDetails.get(0).getMossadId(), currReformType, begda, endda);

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
    public void setRecordForSave(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        List<convertHours> localConvertHours = this.convertHoursService.findAll();
        int reformType = localConvertHours.stream()
                .filter(e -> e.getCode() == teacherEmploymentDetails.get(0)
                        .getEmpCode()).findFirst().get().getReformType();

        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 30);

        for (TeacherEmploymentDetails el : teacherEmploymentDetails) {
            if (el.getBegda() == null) el.setBegda(todayDate);
            if (el.getEndda() == null) el.setEndda(cal.getTime());
            el.setReformType(reformType);
        }
    }

    private void getWeeklyHours(float[] week, String empId, int mossadId, int currReformType, Date begda, Date endda) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(currReformType);

        // get all exist hours (without this current reform, hours to avoid duplication)
        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId, begda, endda))
            if (el.getMossadId() != mossadId || !relevantCodes.contains(el.getEmpCode())) {
                week[el.getDay()] += el.getHours();
            }

    }

    public float[] getWeek(String empId, Date begda, Date endda) {
        float[] week = new float[7];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndDay(empId, i, begda, endda).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        week[6] = this.getEmpJobPercent(empId, begda, endda);
        return week;
    }

    public float[] getWeekPerMossad(String empId, int mossadId, Date begda, Date endda) {
        float[] week = new float[7];
        for (int i = 0; i < 6; i++) {
            week[i] = (float) this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadIdAndDay(empId, mossadId, i, begda, endda).
                    stream().mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }
        week[6] = this.getEmpJobPercentPerMossad(empId, mossadId, begda, endda);
        return week;
    }

    public List<TeacherEmploymentDetails> getAllByReformType(String empId, int mossadId, int reformType, Date begda, Date endda) {

        List<Integer> relevantCodes = this.convertHoursService.getAllByReform(reformType);
        List<TeacherEmploymentDetails> list = new ArrayList<>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda)) {
            if (relevantCodes.contains(el.getEmpCode())) {
                list.add(el);
            }
        }
        return list;
    }

    public List<TeacherEmploymentDetails> getEmpHoursByMossad(String empId, int mossadId, Date begda, Date endda) {
        return this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda);
    }

    public List<TeacherEmploymentDetails> getAllByMossad(int mossadId, Date begda, Date endda) {
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();
        return this.teacherEmploymentDetailsRepository
                .findByMossadId(mossadId, begda, endda).stream()
                .filter(el -> frontalCodes.contains(el.getEmpCode()))
                .collect(Collectors.toList());
    }

    // check for max job percent
    private void checkMaxJobPercent(@NotNull List<TeacherEmploymentDetails> employmentDetails, String empId) {
        int currReformType;
        float maxJobPercet = 0, currJobPercnt = 0, allHours = 0;

        maxJobPercet = this.maxJobPercentById(empId);
        currReformType = this.convertHoursService.findByCode(employmentDetails.get(0).getEmpCode());

        // get the hours of the sent reform type (always send from the client for each change)
        allHours += employmentDetails.stream().
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        currJobPercnt = this.calcHourService.getJobPercent(currReformType, empId, allHours);

        if (currJobPercnt > maxJobPercet) {
            throw new GenericException("לעובד יש מגבלת מגבלת אחוז משרה של " + maxJobPercet + "כעת יש %" + currJobPercnt + "%");
        }
    }

    //  check if there no exception from max hours per mossad
    private void checkMaxHoursPerMossad(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails) {
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

    //this check should be performed only for oz letmura and ofek hadash(reformType 2,5)
    private void checkHoursMatch(List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId,
                                 boolean isMother, int ageHours, int currReformType) {
        float frontalHours, privateHours, pauseHours;
        calcHours calcHours;
        // check if right reform else get out
        if (currReformType != 2 && currReformType != 5) {
            return;
        }
        // get the hours by type(frontal = 1/private = 2/pause = 3)
        List<Integer> frontalCodes = this.convertHoursService.getHoursByType(1);
        List<Integer> privateCodes = this.convertHoursService.getHoursByType(2);
        List<Integer> pauseCodes = this.convertHoursService.getHoursByType(3);

        frontalHours = Math.round((float) teacherEmploymentDetails.stream().
                filter(el -> frontalCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum());

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

        calcHours = this.calcHourService.getByFrontalHours(Math.round(currReformType), isMother, ageHours, frontalHours);

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

    public float getEmpJobPercent(String empId, Date begda, Date endda) {
        final float[] jobPercentSum = {0};

        HashMap<Integer, Float> hoursSum = new HashMap<Integer, Float>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpId(empId, begda, endda)) {
            if (!hoursSum.containsKey(el.getReformType())) {
                hoursSum.put(el.getReformType(), el.getHours());
            } else {
                hoursSum.put(el.getReformType(), el.getHours() + hoursSum.get(el.getReformType()));
            }
        }

        hoursSum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getJobPercent(k, empId, v));
        return jobPercentSum[0];
    }

    public float getEmpJobPercentPerMossad(String empId, int mossadId, Date begda, Date endda) {
        final float[] jobPercentSum = {0};

        HashMap<Integer, Float> hoursSum = new HashMap<Integer, Float>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda)) {
            if (!hoursSum.containsKey(el.getReformType())) {
                hoursSum.put(el.getReformType(), el.getHours());
            } else {
                hoursSum.put(el.getReformType(), el.getHours() + hoursSum.get(el.getReformType()));
            }
        }
        hoursSum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getJobPercent(k, empId, v));
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
