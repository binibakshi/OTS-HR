package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.GapsRewardHours;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;
import teachers.biniProject.Repository.TeachersRewardsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachersRewardsServise {

    @Autowired
    private TeachersRewardsRepository teachersRewardsRepository;

    public TeachersRewards save(TeachersRewards teachersRewards) {
        return this.teachersRewardsRepository.save(teachersRewards);
    }

    public List<TeachersRewards> saveAll(List<TeachersRewards> teachersRewards) {
        teachersRewards.removeIf(el -> el.getRewardId() == 0);
        return this.teachersRewardsRepository.saveAll(teachersRewards);
    }

    public List<TeachersRewards> findAll() {
        return this.teachersRewardsRepository.findAll();
    }

    public List<TeachersRewards> findAllByEmpIdAndMossadIdAndYear(int empId, int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    public List<TeachersRewards> findAllByMossadIdAndYear(int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByMossadIdAndYear(mossadId, year);
    }

    public List<TeachersRewards> findAllByYear(int year) {
        return this.teachersRewardsRepository.findAllByYear(year);
    }

    public void delete(int empId, int rewardId, int mossadId, int year, int teachingClass) {
        teachersRewardsCompositeKey teachersRewardsCompositeKey = new teachersRewardsCompositeKey(empId, rewardId, mossadId, year, teachingClass);
        this.teachersRewardsRepository.deleteById(teachersRewardsCompositeKey);
    }

    public List<GapsRewardHours> findAllGaps(int year, int mossadId, int reformId) {
        List<GapsRewardHours> gapsRewardHours = new ArrayList<>();
        int empCode = (reformId == 5) ? 9671 : 2598;
        List<Object[]> tempList = this.teachersRewardsRepository.findAllGaps(year, mossadId, reformId, empCode);
        tempList.stream().filter(el -> el[2] == null || el[1] != el[2]).forEach(el -> {
            gapsRewardHours.add(new GapsRewardHours(String.valueOf(el[0]),
                    (Double) el[1], (Double) el[2], (String) el[3], (String) el[4]));
        });

        return gapsRewardHours;
    }
}
