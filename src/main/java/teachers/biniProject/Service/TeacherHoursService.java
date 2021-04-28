package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.*;
import teachers.biniProject.HelperClasses.GapsTeacherHours;
import teachers.biniProject.HelperClasses.HoursByEmpIdAndReform;
import teachers.biniProject.Repository.ConvertHoursRepository;
import teachers.biniProject.Repository.JobRewardsRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;
import teachers.biniProject.Repository.TeacherHoursRepository;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherHoursService {

    @Autowired
    private TeacherHoursRepository teacherHoursRepository;

    @Autowired
    private JobRewardsRepository jobRewardsRepository;

    @Autowired
    private ConvertHoursRepository convertHoursRepository;

    @Autowired
    private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

    @Autowired
    private CalcHoursService calcHoursService;

    @Autowired
    private EmployeeService employeeService;

    public TeacherHours findById(int recordkey) {
        return teacherHoursRepository.findById(recordkey).get();
    }

    public List<TeacherHours> findAll() {
        return teacherHoursRepository.findAll();
    }

    public List<TeacherHours> findByMossadIdAndEmpId(String empId, int mossadId, Date begda, Date endda) {
        return this.teacherHoursRepository.findByEmpIdAndMossadId(empId, mossadId, begda, endda);
    }

    public TeacherHours save(TeacherHours teacherHours) {
        this.deleteByEmpCode(teacherHours.getEmpId(), teacherHours.getMossadId(), teacherHours.getEmpCode(), teacherHours.getBegda(), teacherHours.getEndda());
        if (teacherHours.getHours() != 0) {
            return this.teacherHoursRepository.save(teacherHours);
        }
        return null;
    }

    public List<TeacherHours> saveAll(List<TeacherHours> teacherHoursList) {
        List<TeacherHours> teacherHoursReturn = new ArrayList<>();
        for (TeacherHours el : teacherHoursList) {
            teacherHoursReturn.add(this.save(el));
        }
        return teacherHoursReturn;
    }

    // Delete exist reform data and insert
    public List<TeacherHours> cleanSave(List<TeacherHours> teacherHoursList) {
        TeacherHours teacherHours;
        List<Integer> existCodes = this.convertHoursRepository.findAll().stream().
                map(el -> el.getCode()).collect(Collectors.toList());
        List<Integer> distinctRewardsCodes = this.jobRewardsRepository.findAll().stream().
                filter(el -> el.getMinHours() != 0 || el.getMaxHours() != 0).
                distinct().
                map(JobRewards::getJobCode).collect(Collectors.toList());
        // add Bagrut rewards
        distinctRewardsCodes.add(2598);
        distinctRewardsCodes.add(9671);
        //Remove codes in rewards codes or not exist in convertHours table
        teacherHoursList.removeIf(el -> distinctRewardsCodes.contains(el.getEmpCode()) || !existCodes.contains(el.getEmpCode()));

        if (teacherHoursList.isEmpty()) {
            return null;
        }
        teacherHours = teacherHoursList.get(0);
        this.teacherHoursRepository.deleteOverlapps(teacherHours.getEmpId(), teacherHours.getMossadId(), teacherHours.getReformType(), teacherHours.getBegda(), teacherHours.getEndda());
        return this.teacherHoursRepository.saveAll(teacherHoursList.stream().filter(el -> el.getHours() != 0).collect(Collectors.toList()));
    }

    public void deleteByEmpCode(String empId, int mossadId, int empCode, Date begda, Date endda) {
        this.teacherHoursRepository.deleteByEmpIdAndMossadIdAndEmpCode(empId, mossadId, empCode, begda, endda);
    }

//    public List<GapsTeacherHours> findAllGaps(int year, int mossadId) {
//        Date begda = new Date(year - 1900 - 1, 7, 30), endda = new Date(year - 1900, 5, 21);
//        List<GapsTeacherHours> gapsRewardHours = new ArrayList<>();
//        List<Object[]> tempList = this.teacherHoursRepository.findAllGaps(begda, endda, mossadId);
////        GapsRewardHours prevReward = new GapsRewardHours();
//        tempList.stream().forEach(el -> {
//
//            // check if aleady exist empid with same reward type than add to exist hours
//            Optional<GapsTeacherHours> prevReward = gapsRewardHours.stream().
//                    filter(e -> e.getEmpId().equals(String.valueOf(el[0]))).findFirst();
//
//            if (prevReward.isPresent()) {
//                prevReward.get().setEstimateHours(prevReward.get().getEstimateHours() + (Double) el[1]);
//                prevReward.get().setActualHours(prevReward.get().getActualHours() + (Double) el[2]);
//            } else {
//                gapsRewardHours.add(new GapsTeacherHours(String.valueOf(el[0]),
//                        (Double) el[1], (Double) el[2], (String) el[3], (String) el[4]));
//            }
//        });
//
//        return gapsRewardHours;
//    }

    public List<GapsTeacherHours> findAllGaps(int mossadId, int year) {
        CalcHours calcHours;
        List<Employee> employeeList = null;
        Employee employee = null;
        List<Object[]> tempHoursByEmpIdAndReformList;
        List<HoursByEmpIdAndReform> estimateHoursList = new ArrayList<>();
        List<HoursByEmpIdAndReform> actualHoursList = new ArrayList<>();
        GapsTeacherHours gapsTeacherHours;
        float totalHours = 0;
        List<GapsTeacherHours> gapsTeacherHoursList = new ArrayList<>();
        employeeList = this.employeeService.findAll();

        tempHoursByEmpIdAndReformList = this.teacherHoursRepository.empHoursSumByMossadId(mossadId,
                new Date(year - 1900 - 1, Calendar.AUGUST, 30),
                new Date(year - 1900, Calendar.JUNE, 21));
        // cannot get directly from repository so convert it like that
        for (Object[] el : tempHoursByEmpIdAndReformList) {
            estimateHoursList.add(new HoursByEmpIdAndReform((String) el[0], (Double) el[1], (int) el[2]));
        }
        // cannot get directly from repository so convert it like that
        tempHoursByEmpIdAndReformList = teacherEmploymentDetailsRepository.empHoursSumByMossadId(mossadId,
                new Date(year - 1900 - 1, Calendar.AUGUST, 30),
                new Date(year - 1900, Calendar.JUNE, 21));

        for (Object[] el : tempHoursByEmpIdAndReformList) {
            actualHoursList.add(new HoursByEmpIdAndReform((String) el[0], (Double) el[1], (int) el[2]));
        }

        for (HoursByEmpIdAndReform el : estimateHoursList) {
            if (employee == null || employee.getEmpId() != el.getEmpId()) {
                employee = employeeList.stream().filter(e -> e.getEmpId().equals(el.getEmpId())).findFirst().orElse(null);
            }
            calcHours = this.calcHoursService.getByFrontalHours(el.getReformType(), employee.isMother(),
                    this.employeeService.getAgeHours(employee.getBirthDate(), year), Math.round(el.getHours().floatValue()));
            totalHours = (el.getHours().floatValue() + calcHours.getPrivateHours() + calcHours.getPauseHours());
            gapsTeacherHours = gapsTeacherHoursList.stream().filter(e -> e.getEmpId() == el.getEmpId()).
                    findFirst().orElse(null);
            if (gapsTeacherHours == null) {
                gapsTeacherHoursList.add(new GapsTeacherHours(el.getEmpId(), Double.valueOf(totalHours),
                        0.0, employee.getFirstName(), employee.getLastName()));
            } else {
                gapsTeacherHours.setEstimateHours(gapsTeacherHours.getEstimateHours() + el.getHours());
            }
        }

        // not supposed to be null empId record but catch it any way inside if to avoid performance issues
        for (HoursByEmpIdAndReform el : actualHoursList) {
            gapsTeacherHours = gapsTeacherHoursList.stream().filter(e -> e.getEmpId().equals(el.getEmpId())).
                    findFirst().orElse(null);
            if (gapsTeacherHours == null) {
                employee = employeeList.stream().filter(e -> e.equals(el.getEmpId())).findFirst().orElse(null);
                gapsTeacherHoursList.add(new GapsTeacherHours(el.getEmpId(), el.getHours(),
                        0.0, employee.getFirstName(), employee.getLastName()));
            } else {
                gapsTeacherHours.setActualHours(gapsTeacherHours.getActualHours() + el.getHours());
            }
        }
        return gapsTeacherHoursList;
    }
}
