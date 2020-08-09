package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Service.TeacherEmploymentDetailsService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/teacherEmploymentDetails")
public class TeacherEmploymentDetailsController {

    @Autowired
    private TeacherEmploymentDetailsService teacherEmploymentDetailsService;

    @GetMapping("/all")
    public List<TeacherEmploymentDetails> getAll() {
        return teacherEmploymentDetailsService.findAll();
    }

    @PostMapping("/saveAll")
    @Transactional
    public List<TeacherEmploymentDetails> saveAll(@RequestBody List<TeacherEmploymentDetails> teacherEmploymentDetails) {

        return this.teacherEmploymentDetailsService.saveAll(teacherEmploymentDetails);
    }

    @GetMapping("/weekSum")
    public float[] getAllWeekHours(@RequestParam(name = "empId") String empId) {
        return this.teacherEmploymentDetailsService.getWeek(empId);
    }

    @GetMapping("/weekSumPerMossad")
    public float[] getAllWeekHoursPerMossad(@RequestParam(name = "empId") String empId,
                                            @RequestParam(name = "mossadId") int mossadId) {
        return this.teacherEmploymentDetailsService.getWeekPerMossad(empId, mossadId);
    }

    @GetMapping("/byMossad")
    public List<TeacherEmploymentDetails> getEmpHoursByMossad(@RequestParam(name = "empId") String empId,
                                                              @RequestParam(name = "mossadId") int mossadId) {
        return teacherEmploymentDetailsService.getEmpHoursByMossad(empId, mossadId);
    }

    @GetMapping("/allByMossad")
    public List<TeacherEmploymentDetails> getAllByMossad(@RequestParam(name = "mossadId") int mossadId) {
        return teacherEmploymentDetailsService.getAllByMossad( mossadId);
    }

    @GetMapping("/byReform")
    public List<TeacherEmploymentDetails> getAllByReformType(@RequestParam(name = "empId") String empId,
                                                             @RequestParam(name = "mossadId") int mossadId,
                                                             @RequestParam(name = "reformType") int reformType) {

        return teacherEmploymentDetailsService.getAllByReformType(empId, mossadId, reformType);
    }

}
