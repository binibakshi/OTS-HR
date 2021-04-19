package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.ConvertHours;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.GapsRewardHours;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;
import teachers.biniProject.Repository.TeachersRewardsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeachersRewardsServise {

    @Autowired
    private TeachersRewardsRepository teachersRewardsRepository;

    @Autowired
    private ConvertHoursService convertHoursService;

    public TeachersRewards save(TeachersRewards teachersRewards) {
        return this.teachersRewardsRepository.save(teachersRewards);
    }

    public List<TeachersRewards> saveAll(List<TeachersRewards> teachersRewards) {
        teachersRewards.removeIf(el -> el.getRewardId() == 0);
        List<ConvertHours> employmentCodes = this.convertHoursService.findAll();
        teachersRewards.forEach(el -> {
            if (el.getEmploymentCode() == 0) {
                el.setReformId(8); // administraion
            } else {
                el.setReformId(employmentCodes.stream().
                        filter(e -> e.getCode() == el.getEmploymentCode()).findFirst().get().getReformType());
            }
        });
        return this.teachersRewardsRepository.saveAll(teachersRewards);
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
//        Date begda, endda;
//        begda = new Date(year - 1 - 1900, 8, 1);
//        endda = new Date((year - 1900), 6, 20);

        teachersRewardsCompositeKey teachersRewardsCompositeKey = new teachersRewardsCompositeKey(empId, rewardId, rewardType, mossadId, year, teachingClass, grade);
        TeachersRewards teacherRewards = this.teachersRewardsRepository.findById(teachersRewardsCompositeKey).get();
        this.teachersRewardsRepository.delete(teacherRewards);
        // update teacher hours after delete co-responding reward
//        this.teacherEmploymentDetailsService.deleteByEmpIdAndMossadIdAndEmpCode(empId, mossadId, begda, endda, teacherRewards.getEmploymentCode());
    }

    public List<GapsRewardHours> findAllGaps(int year, int mossadId) {
        List<GapsRewardHours> gapsRewardHours = new ArrayList<>();
        List<Object[]> tempList = this.teachersRewardsRepository.findAllGaps(year, mossadId);
//        GapsRewardHours prevReward = new GapsRewardHours();
        tempList.stream().forEach(el -> {

            // check if aleady exist empid with same reward type than add to exist hours
            Optional<GapsRewardHours> prevReward = gapsRewardHours.stream().filter(e -> e.getEmpId().equals(String.valueOf(el[0]))
                    && e.getRewardType() == (Integer) el[5]).findFirst();
            if (prevReward.isPresent()) {
                prevReward.get().setHours(prevReward.get().getHours() + (Double) el[1]);
                prevReward.get().setActualHours(prevReward.get().getActualHours() + (Double) el[2]);
            } else {
                gapsRewardHours.add(new GapsRewardHours(String.valueOf(el[0]),
                        (Double) el[1], (Double) el[2], (String) el[3], (String) el[4], (int) el[5]));
            }
        });

        return gapsRewardHours;
    }
}