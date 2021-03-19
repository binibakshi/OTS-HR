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

    @GetMapping("/byMossadAndYearAndType")
    public List<TeachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year,
                                                          @RequestParam(name = "rewardType") int rewardType) {
        return this.teachersRewardsServise.findAllByMossadIdAndYearAndRewardType(mossadId, year, rewardType);
    }

    @GetMapping("/byMossadAndYear")
    public List<TeachersRewards> findAllByMossadIdAndYear(@RequestParam(name = "mossadId") int mossadId,
                                                          @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByMossadIdAndYear(mossadId, year);
    }

    @GetMapping("/byEmpIdAndMossadAndYearAndType")
    public List<TeachersRewards> findAllByEmpIdAndMossadIdAndYearAndRewardType(@RequestParam(name = "empId") String empId,
                                                                               @RequestParam(name = "mossadId") int mossadId,
                                                                               @RequestParam(name = "year") int year,
                                                                               @RequestParam(name = "rewardType") int rewardType) {
        return this.teachersRewardsServise.findAllByEmpIdAndMossadIdAndYearAndRewardType(empId, mossadId, year, rewardType);
    }

    @GetMapping("/byEmpIdAndMossadAndYear")
    public List<TeachersRewards> findAllByEmpIdAndMossadIdAndYear(@RequestParam(name = "empId") String empId,
                                                                  @RequestParam(name = "mossadId") int mossadId,
                                                                  @RequestParam(name = "year") int year) {
        return this.teachersRewardsServise.findAllByEmpIdAndMossadIdAndYear(empId, mossadId, year);
    }

    @GetMapping("/findGaps")
    public List<GapsRewardHours> findAllGaps(@RequestParam(name = "year") int year,
                                             @RequestParam(name = "mossadId") int mossadId) {
        return this.teachersRewardsServise.findAllGaps(year, mossadId);
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
    public void delete(@RequestParam(name = "empId") String empId,
                       @RequestParam(name = "rewardId") int rewardId,
                       @RequestParam(name = "mossadId") int mossadId,
                       @RequestParam(name = "year") int year,
                       @RequestParam(name = "class") int teachingClass,
                       @RequestParam(name = "rewardType") int rewardType

    ) {
        this.teachersRewardsServise.delete(empId, rewardId, mossadId, year, teachingClass, rewardType);
    }


}
