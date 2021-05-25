package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.ConvertHours;
import teachers.biniProject.Entity.TeacherHours;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.GapsRewardHours;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;
import teachers.biniProject.Repository.TeachersRewardsRepository;

import java.util.*;

@Service
public class TeachersRewardsServise {

    @Autowired
    private TeachersRewardsRepository teachersRewardsRepository;

    @Autowired
    private ConvertHoursService convertHoursService;

    @Autowired
    private TeacherHoursService teacherHoursService;

    public TeachersRewards save(TeachersRewards teachersRewards) {
        TeachersRewards teachersRewardsReturned = this.teachersRewardsRepository.save(teachersRewards);
        Date begda = new Date(teachersRewards.getYear() - 1900 - 1, Calendar.SEPTEMBER, 1),
                endda = new Date(teachersRewards.getYear() - 1900, Calendar.JUNE, 20);
        TeacherHours teacherHours = new TeacherHours(teachersRewards.getEmpId(), begda, endda,
                teachersRewards.getMossadId(), teachersRewards.getEmploymentCode(), teachersRewards.getReformId(), teachersRewards.getHours());
        this.teacherHoursService.save(teacherHours);
        return teachersRewardsReturned;
    }

    public List<TeachersRewards> saveAll(List<TeachersRewards> teachersRewards) {
        teachersRewards.removeIf(el -> el.getRewardId() == 0);
        List<ConvertHours> employmentCodes = this.convertHoursService.findAll();
        List<TeacherHours> teacherHoursList = new ArrayList<>();
        TeacherHours teacherHours;
        List<TeachersRewards> teachersRewardsList;
        for (TeachersRewards el : teachersRewards) {
            if (el.getEmploymentCode() == 0) {
                el.setReformId(8); // administraion
            } else {
                el.setReformId(employmentCodes.stream().
                        filter(e -> e.getCode() == el.getEmploymentCode()).findFirst().get().getReformType());
            }
            teacherHours = teacherHoursList.stream().filter(e -> e.getEmpCode() == el.getEmploymentCode()).findFirst().orElse(null);
            if (teacherHours != null) {
                teacherHours.setHours(teacherHours.getHours() + el.getHours());
            } else {
                // sum all hours by same code
                teacherHoursList.add(new TeacherHours(el.getEmpId(), new Date(el.getYear() - 1900 - 1, Calendar.SEPTEMBER, 1, 12, 0, 0),
                        new Date(el.getYear() - 1900, Calendar.JUNE, 20, 12, 0, 0),
                        el.getMossadId(), el.getEmploymentCode(), el.getReformId(), el.getHours()));
            }
        }

        teachersRewardsList = this.teachersRewardsRepository.saveAll(teachersRewards);
        this.teacherHoursService.saveAll(teacherHoursList);
        return teachersRewardsList;
    }

    public List<TeachersRewards> findAll() {
        return this.teachersRewardsRepository.findAll();
    }

    public List<TeachersRewards> findAllByEmpIdAndMossadIdAndYear(String empId, int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    public List<TeachersRewards> findAllByEmpIdAndMossadIdAndYearAndRewardType(String empId, int mossadId, int year, int rewardType) {
        return this.teachersRewardsRepository.findAllByEmpIdAndMossadIdAndYearAndRewardType(empId, mossadId, year, rewardType);
    }

    public List<TeachersRewards> findAllByMossadIdAndYearAndRewardType(int mossadId, int year, int rewardType) {
        return this.teachersRewardsRepository.findAllByMossadIdAndYearAndRewardType(mossadId, year, rewardType);
    }

    public List<TeachersRewards> findAllByMossadIdAndYear(int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByMossadIdAndYear(mossadId, year);
    }

    public List<TeachersRewards> findAllByYear(int year) {
        return this.teachersRewardsRepository.findAllByYear(year);
    }

    public List<TeachersRewards> findAllByYearAndRewardType(int year, int rewardType) {
        return this.teachersRewardsRepository.findAllByYearAndRewardType(year, rewardType);
    }

    public void delete(String empId, int rewardId, int mossadId, int year, int teachingClass, int grade, int rewardType) {

        teachersRewardsCompositeKey teachersRewardsCompositeKey = new teachersRewardsCompositeKey(empId, rewardId, rewardType, mossadId, year, teachingClass, grade);
        TeachersRewards teacherRewards = this.teachersRewardsRepository.findById(teachersRewardsCompositeKey).get();
        this.teachersRewardsRepository.delete(teacherRewards);
        this.teacherHoursService.updateHours(empId, mossadId, teacherRewards.getEmploymentCode(),
                teacherRewards.getReformId(), year, 0);
        // update teacher hours after delete co-responding reward
//        this.teacherEmploymentDetailsService.deleteByEmpIdAndMossadIdAndEmpCode(empId, mossadId, begda, endda, teacherRewards.getEmploymentCode());
    }

    public List<GapsRewardHours> findAllGaps(int year, int mossadId) {
        List<GapsRewardHours> gapsRewardHours = new ArrayList<>();
        List<Object[]> tempList = this.teachersRewardsRepository.findAllGaps(year, mossadId);
//        GapsRewardHours prevReward = new GapsRewardHours();
        for (Object[] el : tempList) {// check if aleady exist empid with same reward type than add to exist hours
            Optional<GapsRewardHours> prevReward = gapsRewardHours.stream().filter(e -> e.getEmpId().equals(String.valueOf(el[0]))
                    && e.getRewardType() == (Integer) el[5]).findFirst();
            if (prevReward.isPresent()) {
                prevReward.get().setHours(prevReward.get().getHours() + (Double) el[1]);
                prevReward.get().setActualHours(prevReward.get().getActualHours() + (Double) el[2]);
            } else {
                gapsRewardHours.add(new GapsRewardHours(String.valueOf(el[0]),
                        (Double) el[1], (Double) el[2], (String) el[3], (String) el[4], (int) el[5]));
            }
        }

        return gapsRewardHours;
    }
}
