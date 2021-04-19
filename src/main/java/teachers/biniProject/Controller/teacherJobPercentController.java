package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.TeacherJobPercent;
import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;
import teachers.biniProject.Service.TeacherJobPercentService;

import java.util.List;

@RestController
@RequestMapping("/jobPercent")
public class TeacherJobPercentController {

    @Autowired
    TeacherJobPercentService teacherJobPercentService;

    @GetMapping("/all")
    public List<TeacherJobPercent> getAll() {
        return this.teacherJobPercentService.getAll();
    }

    @GetMapping("/byYear")
    public List<TeacherJobPercent> getAllByYear(@RequestParam(name = "year") int year) {
        return this.teacherJobPercentService.getAllByYear(year);
    }

    @GetMapping("/byYearAndMossad")
    public List<TeacherJobPercent> getAllByYearAndMossadId(@RequestParam(name = "mossadId") int mossadId,
                                                           @RequestParam(name = "year") int year) {
        return this.teacherJobPercentService.getAllByYearAndMossadId(year, mossadId);
    }

    @GetMapping("/byId")
    public TeacherJobPercent getById(@RequestParam(name = "empId") String empId,
                                     @RequestParam(name = "mossadId") int mossadId,
                                     @RequestParam(name = "year") int year) {
        return this.teacherJobPercentService.getById(new EmpIdYearComositeKey(empId, mossadId, year));
    }

    @PostMapping("/save")
    public TeacherJobPercent getById(@RequestBody TeacherJobPercent teacherJobPercent) {
        return this.teacherJobPercentService.save(teacherJobPercent);
    }

    @PostMapping("/saveAll")
    public List<TeacherJobPercent> getById(@RequestBody List<TeacherJobPercent> teacherJobPercent) {
        return this.teacherJobPercentService.saveAll(teacherJobPercent);
    }
}
