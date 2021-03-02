package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.JobRewards;
import teachers.biniProject.Repository.JobRewardsRepository;
import teachers.biniProject.Repository.TeachersRewardsRepository;

import java.util.List;

@RestController
@RequestMapping("/jobRewards")
public class JobRewardsController {

    @Autowired
    private JobRewardsRepository jobRewardsRepository;

    @Autowired
    private TeachersRewardsRepository teachersRewardsRepository;

    @GetMapping("/all")
    public List<JobRewards> getAll() {
        return jobRewardsRepository.findAll();
    }

    @PostMapping("/saveAll")
    public void save(@RequestBody List<JobRewards> jobRewerds) {
        this.jobRewardsRepository.saveAll(jobRewerds);
    }

    @PostMapping("/save")
    public JobRewards save(@RequestBody JobRewards jobRewerds) {
        return this.jobRewardsRepository.save(jobRewerds);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody JobRewards jobRewerds) {
        this.jobRewardsRepository.delete(jobRewerds);
        this.teachersRewardsRepository.deleteAllByReformIdAndRewardType(jobRewerds.getRecordkey(), 1);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.jobRewardsRepository.deleteAll();
    }

    @DeleteMapping("/deleteSelected")
    public void deleteSelected(@RequestBody List<JobRewards> jobRewardsList) {
        this.jobRewardsRepository.deleteAll(jobRewardsList);
    }
}

