package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.teachersRewards;
import teachers.biniProject.Service.teachersRewardsServise;

import java.util.List;

@RestController
@RequestMapping("/teachersRewards")
public class teachersRewardsController {

    @Autowired
    private teachersRewardsServise teachersRewardsServise;

    @GetMapping("/all")
    public List<teachersRewards> getAll() {
        return this.teachersRewardsServise.findAll();
    }

    @GetMapping("/byMossadAndYear")
    public List<teachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByMossadIdAndYear(mossadId, year);
    }

    @GetMapping("/byEmpIdAndMossadAndYear")
    public List<teachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "empId") int empId,
                                                          @RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    @PostMapping("/saveAll")
    public List<teachersRewards> saveAll(@RequestBody List<teachersRewards> teachersRewards) {
        return this.teachersRewardsServise.saveAll(teachersRewards);
    }

    @PostMapping("/save")
    public teachersRewards save(@RequestBody teachersRewards teachersRewards) {
        return this.teachersRewardsServise.save(teachersRewards);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "empId") int empId,
                       @RequestParam(name = "rewardId") int rewardId,
                       @RequestParam(name = "mossadId") int mossadId,
                       @RequestParam(name = "year") int year,
                       @RequestParam(name = "class") int teachingClass
    ) {
        this.teachersRewardsServise.delete(empId, rewardId, mossadId, year, teachingClass);
    }


}
