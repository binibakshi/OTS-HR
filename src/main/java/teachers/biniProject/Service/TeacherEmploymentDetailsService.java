package teachers.biniProject.Service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.*;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;
import teachers.biniProject.HelperClasses.MossadHoursComositeKey;
import teachers.biniProject.Repository.MossadHoursRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;
import teachers.biniProject.Security.MyUserDetailsService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TeacherEmploymentDetailsService {

    private final float MAX_HOURS_PER_DAY = 9;
    private final float MAX_HOURS_FRIDAY = 6;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ConvertHoursService convertHoursService;
    @Autowired
    private CalcHoursService calcHourService;
    @Autowired
    private MossadHoursRepository mossadHoursRepository;
    @Autowired
    private TeacherJobPercentService teacherJobPercentService;

    public List<TeacherEmploymentDetails> getReportSelection(Date begda, Date endda, List<Integer> mossadId, List<Integer> reformType,
                                                             List<Integer> empCode, List<Character> status) {
        List<TeacherEmploymentDetails> selectedRecords = this.teacherEmploymentDetailsRepository.findByBegdaAfterAndEnddaBefore(begda, endda);
        if (mossadId != null && mossadId.size() != 0) {
            selectedRecords.removeIf(el -> !mossadId.contains(el.getMossadId()));
        }
        if (reformType != null && reformType.size() != 0) {
            selectedRecords.removeIf(el -> !reformType.contains(el.getReformType()));
        }
        if (empCode != null && empCode.size() != 0) {
            selectedRecords.removeIf(el -> !empCode.contains(el.getEmpCode()));
        }
        if (status != null && status.size() != 0) {
            selectedRecords.removeIf(el -> !status.contains(el.getStatus()));
        }

        return selectedRecords;
    }

    public List<TeacherEmploymentDetails> findAll() {
        return teacherEmploymentDetailsRepository.findAll();
    }

    public void deleteByEmpId(String empId) {
        this.teacherEmploymentDetailsRepository.deleteByEmpId(empId);
    }

    public List<TeacherEmploymentDetails> saveAll(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        int mossadId, reformType, year;
        double oldHours, newHours = 0;
        float currjobPercent;
        String empId;
        Date begda, endda;
        Calendar cal = Calendar.getInstance();
        TeacherJobPercent teacherJobPercent;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();
        List<TeacherEmploymentDetails> existTeacherEmploymentHours;

        if (teacherEmploymentDetails.isEmpty()) {
            throw new GenericException("לא נמצאו נתונים");
        }
        //  set some values
        this.setRecordForSave(teacherEmploymentDetails);
        empId = teacherEmploymentDetails.get(0).getEmpId();
        mossadId = teacherEmploymentDetails.get(0).getMossadId();
        reformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());
        begda = teacherEmploymentDetails.get(0).getBegda();
        endda = teacherEmploymentDetails.get(0).getEndda();
        cal.setTime(begda);
        year = cal.get(Calendar.YEAR);
        if (cal.get(Calendar.MONTH) >= 8) {
            year++;
        }
        //Admin hours
        if (reformType == 8) {
            //TODO:return
        }
        // get all exist teacher employment hours per mossad for all reformTypes(0 = all)
        existTeacherEmploymentHours = this.teacherEmploymentDetailsRepository.findOverlapping(empId, mossadId, 0, begda, endda);
        teacherJobPercent = this.teacherJobPercentService.getById(new EmpIdYearComositeKey(empId, mossadId, year));
        if (teacherJobPercent == null) {
            teacherJobPercent = new TeacherJobPercent(empId, mossadId, year, 0, 0);
        }
        // remove unnecessary items
        teacherEmploymentDetails = teacherEmploymentDetails.stream().filter(el -> el.getHours() != 0).collect(Collectors.toList());

        if (!teacherEmploymentDetails.isEmpty()) {
            // Check if there is estimateJobPercent
            if (teacherJobPercent.getEstimateJobPercent() == 0) {
                throw new GenericException("יש ליצור אחוז קביעות לעובד זה");
            }
            // do checks only in insert aviod delete
            // check validation raise exception if needed (set teacherJobPercent with value)
            this.checkNewHoursValidation(teacherEmploymentDetails, begda, endda, existTeacherEmploymentHours);
            // Set current job percent
            currjobPercent = this.getMossadJobPercent(teacherEmploymentDetails, empId, reformType, year, existTeacherEmploymentHours);
        } else {
            // when delete all set job percent to 0
            currjobPercent = 0;
        }

        // TODO check usage of findOverlapping method
        oldHours = existTeacherEmploymentHours.stream().filter(el -> el.getMossadId() == mossadId && frontalCodes.contains(el.getEmpCode()))
                .mapToDouble(TeacherEmploymentDetails::getHours).sum();

        if (!teacherEmploymentDetails.isEmpty()) {
            newHours = teacherEmploymentDetails.stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                    .mapToDouble(TeacherEmploymentDetails::getHours).sum();
        }

        // TODO: temperary until timeConstraint fix delete and save all
        this.teacherEmploymentDetailsRepository.deleteOverlapps(empId, mossadId, reformType, begda, endda);
        this.teacherEmploymentDetailsRepository.saveAll(teacherEmploymentDetails);

        teacherJobPercent.setJobPercent(currjobPercent);
        this.teacherJobPercentService.save(teacherJobPercent);
        this.updateMossadHours(mossadId, year, (float) (newHours - oldHours));
        return teacherEmploymentDetails;
    }

    // Set some values
    public void setRecordForSave(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        List<ConvertHours> localConvertHours = this.convertHoursService.findAll();
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

    private void getWeeklyHours(float[] week, String empId, int mossadId, int currReformType, List<TeacherEmploymentDetails> TeacherEmploymentDetailsAll) {

        // get all exist hours (without this current reform, hours to avoid duplication)
        for (TeacherEmploymentDetails el : TeacherEmploymentDetailsAll)
            if (el.getMossadId() != mossadId || el.getReformType() != currReformType) {
                week[el.getDay()] += el.getHours();
            }

    }

    public List<TeacherEmploymentDetails> getAllByReformType(String empId, int mossadId, int reformType, Date begda, Date endda) {

        List<TeacherEmploymentDetails> list = new ArrayList<>();

        for (TeacherEmploymentDetails el : this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda)) {
            if (el.getReformType() == reformType) {
                list.add(el);
            }
        }
        return list;
    }

    public List<TeacherEmploymentDetails> getEmpHoursByMossad(String empId, int mossadId, Date begda, Date endda) {
        return this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda);
    }

    public List<TeacherEmploymentDetails> getByEmpId(String empId, Date begda, Date endda) {
        return this.teacherEmploymentDetailsRepository.findByEmpId(empId, begda, endda);
    }

    public List<TeacherEmploymentDetails> getAllByMossad(int mossadId, Date begda, Date endda) {
        return this.teacherEmploymentDetailsRepository
                .findByMossadId(mossadId, begda, endda);
    }

    // validate checks raise exceptions if needed
    private void checkNewHoursValidation(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails,
                                         Date begda, Date endda, List<TeacherEmploymentDetails> TeacherEmploymentDetailsAll) {
        int currReformType, year;
        String empId, userName;
        Calendar cal = Calendar.getInstance();
        Employee employee;

        // get the current reform type
        currReformType = this.convertHoursService.findByCode(teacherEmploymentDetails.get(0).getEmpCode());
        cal.setTime(begda);
        year = cal.get(Calendar.YEAR);
        if (cal.get(Calendar.MONTH) >= 8) {
            year++;
        }

        empId = teacherEmploymentDetails.get(0).getEmpId();
        userName = teacherEmploymentDetails.get(0).getChangedBy();
        employee = this.employeeService.findById(empId);

        this.checkHoursMatch(teacherEmploymentDetails, empId, employee.isMother(),
                employeeService.getAgeHours(employee.getBirthDate(), year), currReformType);

        // Get the current userName if is admin user skip these checks
        UserDetails user = myUserDetailsService.loadUserByUsername(userName);
        if (user.getAuthorities().stream().anyMatch(el -> el.getAuthority().equals("ROLE_ADMIN"))) {
            return;
        }
        this.checkMaxHoursInDay(teacherEmploymentDetails, empId, currReformType, begda, endda, TeacherEmploymentDetailsAll);
        this.checkMaxJobPercent(teacherEmploymentDetails, empId, currReformType, begda, endda, year, TeacherEmploymentDetailsAll);
        this.checkMaxHoursPerMossad(teacherEmploymentDetails, year);
    }

    private void checkMaxHoursInDay(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId, int currReformType, Date begda, Date endda, List<TeacherEmploymentDetails> TeacherEmploymentDetailsAll) {
        float[] week = new float[6];
        Arrays.fill(week, 0);

        this.getWeeklyHours(week, empId, teacherEmploymentDetails.get(0).getMossadId(), currReformType, TeacherEmploymentDetailsAll);

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

    // check for max job percent
    private void checkMaxJobPercent(@NotNull List<TeacherEmploymentDetails> employmentDetails,
                                    String empId, int currReformType, Date begda, Date endda, int year,
                                    List<TeacherEmploymentDetails> TeacherEmploymentDetailsAll) {
        int mossadId;
        float maxJobPercet;
        final float[] jobPercentSum = {0};
        HashMap<Integer, Float> hoursSum = new HashMap<Integer, Float>();
        mossadId = employmentDetails.get(0).getMossadId();
        maxJobPercet = this.maxJobPercentById(empId, begda, endda, currReformType);

        // add new hours
        hoursSum.put(currReformType, (float) employmentDetails.stream().
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum());

        // get all exist hours (without this current reform per mossad, hours to avoid duplication)
        for (TeacherEmploymentDetails el : TeacherEmploymentDetailsAll)
            if (el.getMossadId() != mossadId || el.getReformType() != currReformType) {
                if (!hoursSum.containsKey(el.getReformType())) {
                    hoursSum.put(el.getReformType(), el.getHours());
                } else {
                    hoursSum.put(el.getReformType(), el.getHours() + hoursSum.get(el.getReformType()));
                }
            }
        // sum all by reform
        hoursSum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getJobPercent(k, empId, v, year));

        if (jobPercentSum[0] > maxJobPercet) {
            throw new GenericException("לעובד יש מגבלת מגבלת אחוז משרה של " + maxJobPercet + "כעת יש %" + jobPercentSum[0] + "%");
        }
    }

    //  check if there no exception from max hours per mossad
    private void checkMaxHoursPerMossad(@NotNull List<TeacherEmploymentDetails> teacherEmploymentDetails, int year) {
        int mossadId = teacherEmploymentDetails.get(0).getMossadId();
        double newHours, currHoursPerMossad, maxHoursPerMossad;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();

        newHours = teacherEmploymentDetails.stream().
                filter(el -> frontalCodes.contains(el.getEmpCode())).
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum();

        currHoursPerMossad = mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get().getCurrHours();
        maxHoursPerMossad = mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get().getMaxHours();

        if ((newHours + currHoursPerMossad) > maxHoursPerMossad) {
            throw new GenericException("למוסד יש מגבלת של" + maxHoursPerMossad + "שעות");
        }
    }

    //this check should be performed only for oz letmura and ofek hadash(reformType 2,5)
    private void checkHoursMatch(List<TeacherEmploymentDetails> teacherEmploymentDetails, String empId,
                                 boolean isMother, int ageHours, int currReformType) {
        float frontalHours, privateHours, pauseHours;
        CalcHours calcHours;
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
    private void updateMossadHours(int mossadId, int year, float hourToAdd) {
        // set the new hours in total sum hours per mossad
        MossadHours mossadHours = this.mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get();
        mossadHours.setCurrHours(mossadHours.getCurrHours() + hourToAdd);
        this.mossadHoursRepository.save(mossadHours);
    }

    public float getMossadJobPercent(@NotNull List<TeacherEmploymentDetails> employmentDetails,
                                     String empId, int currReformType, int year,
                                     List<TeacherEmploymentDetails> TeacherEmploymentDetailsAll) {
        int mossadId;
        final float[] jobPercentSum = {0};
        HashMap<Integer, Float> hoursSum = new HashMap<Integer, Float>();
        mossadId = employmentDetails.get(0).getMossadId();

        // add new hours
        hoursSum.put(currReformType, (float) employmentDetails.stream().
                mapToDouble(TeacherEmploymentDetails::getHours).
                sum());

        // get mossad exist hours (without this current reform per mossad, hours to avoid duplication)
        for (TeacherEmploymentDetails el : TeacherEmploymentDetailsAll.stream().
                filter(e -> e.getMossadId() == mossadId && e.getReformType() != currReformType).collect(Collectors.toList()))
            if (!hoursSum.containsKey(el.getReformType())) {
                hoursSum.put(el.getReformType(), el.getHours());
            } else {
                hoursSum.put(el.getReformType(), el.getHours() + hoursSum.get(el.getReformType()));
            }
        // sum all by reform
        hoursSum.forEach((k, v) ->
                jobPercentSum[0] += this.calcHourService.getJobPercent(k, empId, v, year));

        return jobPercentSum[0];
    }

    private float maxJobPercentById(String empId, Date begda, Date endda, int currReformType) {
        List<Integer> currReforms = this.teacherEmploymentDetailsRepository.getReformTypeByEmpId(empId, begda, endda);
        if (!currReforms.contains(currReformType)) currReforms.add(currReformType);

        if (currReforms.contains(2) || currReforms.contains(3) || currReforms.contains(4)) return 117;
        else if (currReforms.contains(5) || currReforms.contains(6)) return 125;
        else if (currReforms.contains(1) || currReforms.contains(7)) return 140;
        else return 100;
    }


}
