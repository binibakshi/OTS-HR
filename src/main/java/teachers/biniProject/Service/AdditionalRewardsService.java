package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.AdditionalRewards;
import teachers.biniProject.Repository.AdditionalRewardsRepository;

@Service
public class AdditionalRewardsService {

    @Autowired
    private AdditionalRewardsRepository additionalRewardsRepository;

    public AdditionalRewards save(AdditionalRewards additionalRewards) {
        AdditionalRewards returnObj = additionalRewardsRepository.save(additionalRewards);

        if (additionalRewards.getReformId() == 1) {
            // TODO blabla

        } else if (additionalRewards.getReformId() == 5) {
            // TODO blabla
        }

        return returnObj;
    }
}
