package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.BagrutRewards;
import teachers.biniProject.Repository.BagrutRewardsRepository;

import java.util.List;

@RestController
@RequestMapping("/bagrutRewards")
public class BagrutRewardsController {

    @Autowired
    private BagrutRewardsRepository bagrutRewardsRepository;

    @GetMapping("/all")
    public List<BagrutRewards> getAll() {
        return bagrutRewardsRepository.findAll();
    }

    @PostMapping("/saveAll")
    public void save(List<BagrutRewards> bagrutRewerds) {
        this.bagrutRewardsRepository.saveAll(bagrutRewerds);
    }

    @PostMapping("/save")
    public BagrutRewards save(BagrutRewards bagrutRewerds) {
        return this.bagrutRewardsRepository.save(bagrutRewerds);
    }

    @DeleteMapping("/delete")
    public void delete(BagrutRewards bagrutRewerds) {
        this.bagrutRewardsRepository.delete(bagrutRewerds);
    }
}
