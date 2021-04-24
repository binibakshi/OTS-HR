package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.JobRewards;
import teachers.biniProject.Entity.TeacherHours;
import teachers.biniProject.HelperClasses.GapsTeacherHours;
import teachers.biniProject.Repository.ConvertHoursRepository;
import teachers.biniProject.Repository.JobRewardsRepository;
import teachers.biniProject.Repository.TeacherHoursRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherHoursService {

    @Autowired
    private TeacherHoursRepository teacherHoursRepository;

    @Autowired
    private JobRewardsRepository jobRewardsRepository;

    @Autowired
    private ConvertHoursRepository convertHoursRepository;

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
        return this.teacherHoursRepository.saveAll(teacherHoursList);
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

    public List<GapsTeacherHours> findAllGaps(int year, int mossadId) {
        List<GapsTeacherHours> gapsRewardHours = new ArrayList<>();
        List<Object[]> tempList = this.teacherHoursRepository.findAllGaps(year, mossadId);
//        GapsRewardHours prevReward = new GapsRewardHours();
        tempList.stream().forEach(el -> {

            // check if aleady exist empid with same reward type than add to exist hours
            Optional<GapsTeacherHours> prevReward = gapsRewardHours.stream().
                    filter(e -> e.getEmpId().equals(String.valueOf(el[0]))).findFirst();

            if (prevReward.isPresent()) {
                prevReward.get().setEstimateHours(prevReward.get().getEstimateHours() + (Double) el[1]);
                prevReward.get().setActualHours(prevReward.get().getActualHours() + (Double) el[2]);
            } else {
                gapsRewardHours.add(new GapsTeacherHours(String.valueOf(el[0]),
                        (Double) el[1], (Double) el[2], (String) el[3], (String) el[4]));
            }
        });

        return gapsRewardHours;
    }
}
