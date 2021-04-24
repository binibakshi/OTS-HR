package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.TeacherHours;
import teachers.biniProject.HelperClasses.GapsTeacherHours;
import teachers.biniProject.Service.TeacherHoursService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/teacherHours")
public class TeacherHoursController {

    @Autowired
    private TeacherHoursService teacherHoursService;

    @PostMapping("/save")
    public List<TeacherHours> save(@RequestBody TeacherHours teacherHours) {
        List<TeacherHours> teacherHoursList = new ArrayList<>();
        teacherHoursList.add(teacherHours);
        return this.teacherHoursService.cleanSave(teacherHoursList);
    }

    @PostMapping("/saveAll")
    public List<TeacherHours> cleanSaveAll(@RequestBody List<TeacherHours> teacherHoursList) {
        return this.teacherHoursService.cleanSave(teacherHoursList);
    }

    @GetMapping("/byEmpIdAndMossadId")
    @Transactional
    public List<TeacherHours> getByEmpAndMossad(@RequestParam(name = "empId") String empId,
                                                @RequestParam(name = "mossadId") int mossadId,
                                                @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                                @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return this.teacherHoursService.findByMossadIdAndEmpId(empId, mossadId, begda, endda);
    }

    @GetMapping("/findGaps")
    public List<GapsTeacherHours> findAllGaps(@RequestParam(name = "year") int year,
                                              @RequestParam(name = "mossadId") int mossadId) {
        return this.teacherHoursService.findAllGaps(year, mossadId);
    }

}
