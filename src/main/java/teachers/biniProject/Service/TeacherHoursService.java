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
        this.updateHours(teacherHours.getEmpId(), teacherHours.getMossadId(), teacherHours.getEmpCode(),
                teacherHours.getReformType(), teacherHours.getBegda().getYear() + 1900 + 1, teacherHours.getHours());
        return teacherHours;
    }

    public List<TeacherHours> saveAll(List<TeacherHours> teacherHoursList) {
        List<TeacherHours> teacherHoursReturn = new ArrayList<>();
        TeacherHours teacherHours;
        for (TeacherHours el : teacherHoursList) {
            teacherHours = this.save(el);
            teacherHoursReturn.add(teacherHours);
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

    //TODO fex this spagetei code
    // error with date i did some fixes
    public void updateHours(String empId, int mossadId, int empCode, int reformType,
                            int year, float hours) {
        Date begda, endda, minBegda, maxEndda;
        begda = new Date(year - 1900 - 1, Calendar.SEPTEMBER, 1, 12, 0, 0);
        endda = new Date(year - 1900, Calendar.JUNE, 20, 12, 0, 0);
        minBegda = new Date(year - 1900 - 1, Calendar.SEPTEMBER, 1, 0, 0, 0);
        maxEndda = new Date(year - 1900, Calendar.JUNE, 21, 12, 0, 0);

        TeacherHours existTeacherHours = this.teacherHoursRepository.
                findByEmpIdAndMossadIdAndEmpCode(empId, mossadId, empCode,
                        minBegda, maxEndda).
                stream().findFirst().orElse(null);

        if (existTeacherHours != null) {
            existTeacherHours.setHours(hours);
            if (existTeacherHours.getHours() == 0) {
                this.deleteByEmpCode(empId, mossadId, empCode, minBegda, maxEndda);
            } else {
                this.teacherHoursRepository.save(existTeacherHours);
            }
        } else {
            this.teacherHoursRepository.save(new TeacherHours(empId, begda, endda, mossadId, empCode, reformType, hours));
        }
    }

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
            gapsTeacherHours = gapsTeacherHoursList.stream().filter(e ->  e.getEmpId().equals(el.getEmpId())).
                    findFirst().orElse(null);
            if (gapsTeacherHours == null) {
                gapsTeacherHoursList.add(new GapsTeacherHours(el.getEmpId(), Double.valueOf(totalHours),
                        0.0, employee.getFirstName(), employee.getLastName()));
            } else {
                gapsTeacherHours.setEstimateHours(gapsTeacherHours.getEstimateHours() + totalHours);
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
