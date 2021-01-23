package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.teachersRewards;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;
import teachers.biniProject.Repository.teachersRewardsRepository;

import java.util.List;

@Service
public class teachersRewardsServise {

    @Autowired
    private teachersRewardsRepository teachersRewardsRepository;

    public teachersRewards save(teachersRewards teachersRewards) {
        return this.teachersRewardsRepository.save(teachersRewards);
    }

    public List<teachersRewards> saveAll(List<teachersRewards> teachersRewards) {
        teachersRewards.removeIf(el -> el.getRewardId() == 0);
        return this.teachersRewardsRepository.saveAll(teachersRewards);
    }

    public List<teachersRewards> findAll() {
        return this.teachersRewardsRepository.findAll();
    }

    public List<teachersRewards> findAllByEmpIdAndMossadIdAndYear(int empId, int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    public List<teachersRewards> findAllByMossadIdAndYear(int mossadId, int year) {
        return this.teachersRewardsRepository.findAllByMossadIdAndYear(mossadId, year);
    }

    public List<teachersRewards> findAllByYear(int year) {
        return this.teachersRewardsRepository.findAllByYear(year);
    }

    public void delete(int empId, int rewardId, int mossadId, int year, int teachingClass) {
        teachersRewardsCompositeKey teachersRewardsCompositeKey = new teachersRewardsCompositeKey(empId, rewardId, mossadId, year, teachingClass);
        this.teachersRewardsRepository.deleteById(teachersRewardsCompositeKey);
    }
}
