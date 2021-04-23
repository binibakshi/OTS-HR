package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.TeacherHours;
import teachers.biniProject.HelperClasses.GapsTeacherHours;
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

    public void updateTeacherHours(List<TeacherHours> teacherHoursList) {

        // Delete old hours of same empCode and insert
        // (in order to be abke to update begda endda if nedded)
        teacherHoursList.forEach(el -> {
            this.save(el);
        });
    }

    public List<TeacherHours> saveAll(List<TeacherHours> teacherHoursList) {
        return this.teacherHoursRepository.saveAll(teacherHoursList);
    }

    // Delete exist reform data and insert
    public List<TeacherHours> cleanSave(List<TeacherHours> teacherHoursList) {
        TeacherHours teacherHours;
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
