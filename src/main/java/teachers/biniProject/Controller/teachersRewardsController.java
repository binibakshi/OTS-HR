package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.GapsRewardHours;
import teachers.biniProject.Service.TeachersRewardsServise;

import java.util.List;

@RestController
@RequestMapping("/teachersRewards")
public class TeachersRewardsController {

    @Autowired
    private TeachersRewardsServise teachersRewardsServise;

    @GetMapping("/all")
    public List<TeachersRewards> getAll() {
        return this.teachersRewardsServise.findAll();
    }

    @GetMapping("/byMossadAndYear")
    public List<TeachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByMossadIdAndYear(mossadId, year);
    }

    @GetMapping("/byEmpIdAndMossadAndYear")
    public List<TeachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "empId") int empId,
                                                          @RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    @GetMapping("/findGaps")
    public List<GapsRewardHours> findAllGaps(@RequestParam(name = "year") int year,
                                             @RequestParam(name = "mossadId") int mossadId,
                                             @RequestParam(name = "reformId") int reformId) {
        return this.teachersRewardsServise.findAllGaps(year, mossadId, reformId);
    }

    @PostMapping("/saveAll")
    public List<TeachersRewards> saveAll(@RequestBody List<TeachersRewards> teachersRewards) {
        return this.teachersRewardsServise.saveAll(teachersRewards);
    }

    @PostMapping("/save")
    public TeachersRewards save(@RequestBody TeachersRewards teachersRewards) {
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
