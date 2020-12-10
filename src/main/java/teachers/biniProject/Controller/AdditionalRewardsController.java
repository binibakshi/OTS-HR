package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.AdditionalRewerds;
import teachers.biniProject.Repository.AdditionalRewardsRepository;

import java.util.List;

@RestController
@RequestMapping("/additionalRewards")
public class AdditionalRewardsController {

    @Autowired
    private AdditionalRewardsRepository additionalRewardsRepository;

    @GetMapping("/all")
    public List<AdditionalRewerds> getAll() {
        return additionalRewardsRepository.findAll();
    }

    @PostMapping("/saveAll")
    public void save(List<AdditionalRewerds> additionalRewerds) {
        this.additionalRewardsRepository.saveAll(additionalRewerds);
    }

    @PostMapping("/save")
    public AdditionalRewerds save(AdditionalRewerds additionalRewerds) {
        return this.additionalRewardsRepository.save(additionalRewerds);
    }

    @DeleteMapping("/delete")
    public void delete(AdditionalRewerds additionalRewerds) {
        this.additionalRewardsRepository.delete(additionalRewerds);
    }
}
