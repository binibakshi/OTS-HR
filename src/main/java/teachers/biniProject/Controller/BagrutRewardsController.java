package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.BagrutRewards;
import teachers.biniProject.Repository.BagrutRewardsRepository;
import teachers.biniProject.Repository.TeachersRewardsRepository;

import java.util.List;

@RestController
@RequestMapping("/bagrutRewards")
public class BagrutRewardsController {

    @Autowired
    private BagrutRewardsRepository bagrutRewardsRepository;

    @Autowired
    private TeachersRewardsRepository teachersRewardsRepository;

    @GetMapping("/all")
    public List<BagrutRewards> getAll() {
        return bagrutRewardsRepository.findAll();
    }

    @PostMapping("/saveAll")
    public void save(@RequestBody List<BagrutRewards> bagrutRewerds) {
        this.bagrutRewardsRepository.saveAll(bagrutRewerds);
    }

    @PostMapping("/save")
    public BagrutRewards save(@RequestBody BagrutRewards bagrutRewerds) {
        return this.bagrutRewardsRepository.save(bagrutRewerds);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody BagrutRewards bagrutRewerds) {
        this.bagrutRewardsRepository.delete(bagrutRewerds);
        this.teachersRewardsRepository.deleteAllByReformId(bagrutRewerds.getRecordkey());
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.bagrutRewardsRepository.deleteAll();
    }

    @DeleteMapping("/deleteSelected")
    public void deleteSelected(@RequestBody List<BagrutRewards> bagrutRewardsList) {
        this.bagrutRewardsRepository.deleteAll(bagrutRewardsList);
    }
}

