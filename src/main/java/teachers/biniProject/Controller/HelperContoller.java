package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Entity.TeachersReforms;
import teachers.biniProject.Repository.EmployeeRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;
import teachers.biniProject.Resources.structForSelection;
import teachers.biniProject.Service.TeacherEmploymentDetailsService;
import teachers.biniProject.Service.helperService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/helper")
public class HelperContoller {

    @Autowired
    private helperService helperService;

    @Autowired
    private EmployeeRepository EmployeeRepository;

    @Autowired
    private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

    @Autowired
    private TeacherEmploymentDetailsService teacherEmploymentDetailsService;


    @GetMapping("/all")
    public List<TeachersReforms> getAll() {
        return this.helperService.getAll();
    }

    @PostMapping("/save")
    public TeachersReforms save(@RequestBody TeachersReforms teachersReforms) {
        return helperService.save(teachersReforms);
    }
}
