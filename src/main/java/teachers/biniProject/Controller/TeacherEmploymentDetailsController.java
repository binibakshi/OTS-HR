package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Service.TeacherEmploymentDetailsService;

import javax.transaction.Transactional;
import java.util.Date;
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
    public float[] getAllWeekHours(@RequestParam(name = "empId") String empId,
                                   @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                   @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return this.teacherEmploymentDetailsService.getWeek(empId, begda, endda);
    }

    @GetMapping("/weekSumPerMossad")
    public float[] getAllWeekHoursPerMossad(@RequestParam(name = "empId") String empId,
                                            @RequestParam(name = "mossadId") int mossadId,
                                            @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                            @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return this.teacherEmploymentDetailsService.getWeekPerMossad(empId, mossadId, begda, endda);
    }

    @GetMapping("/byMossad")
    public List<TeacherEmploymentDetails> getEmpHoursByMossad(@RequestParam(name = "empId") String empId,
                                                              @RequestParam(name = "mossadId") int mossadId,
                                                              @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                                              @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return teacherEmploymentDetailsService.getEmpHoursByMossad(empId, mossadId, begda, endda);
    }

    @GetMapping("/allByMossad")
    public List<TeacherEmploymentDetails> getAllByMossad(@RequestParam(name = "mossadId") int mossadId,
                                                         @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                                         @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return teacherEmploymentDetailsService.getAllByMossad(mossadId, begda, endda);
    }

    @GetMapping("/byReform")
    public List<TeacherEmploymentDetails> getAllByReformType(@RequestParam(name = "empId") String empId,
                                                             @RequestParam(name = "mossadId") int mossadId,
                                                             @RequestParam(name = "reformType") int reformType,
                                                             @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                                             @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {

        return teacherEmploymentDetailsService.getAllByReformType(empId, mossadId, reformType, begda, endda);
    }

}
