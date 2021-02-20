package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.AdditionalRewards;
import teachers.biniProject.Repository.AdditionalRewardsRepository;

import java.util.List;


@RestController
@RequestMapping("/additionalRewards")
public class AdditionalRewardsController {

    @Autowired
    private AdditionalRewardsRepository additionalRewardsRepository;

    @GetMapping("/all")
    public List<AdditionalRewards> getAll() {
        return additionalRewardsRepository.findAll();
    }

    @PostMapping("/saveAll")
    public void save(@RequestBody List<AdditionalRewards> additionalRewerds) {
        this.additionalRewardsRepository.saveAll(additionalRewerds);
    }

    @PostMapping("/save")
    public AdditionalRewards save(@RequestBody AdditionalRewards additionalRewards) {
        return this.additionalRewardsRepository.save(additionalRewards);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody AdditionalRewards additionalRewards) {
        this.additionalRewardsRepository.delete(additionalRewards);
    }
}
