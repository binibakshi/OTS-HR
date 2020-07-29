package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Entity.TeachersReforms;
import teachers.biniProject.Repository.EmployeeRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;
import teachers.biniProject.Resources.structForSelection;
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

    @GetMapping("/test2")
    public List<TeacherEmploymentDetails> getByEmpAndMossad(@RequestParam(name = "empId") String empId,
                                                            @RequestParam(name = "mossadId") int mossadId) {
        return this.teacherEmploymentDetailsRepository.findByEmpIdAndMossadId(empId, mossadId);
    }

    @GetMapping("/all")
    public List<TeachersReforms> getAll() {
        return this.helperService.getAll();
    }

    @PostMapping("/save")
    public TeachersReforms save(@RequestBody TeachersReforms teachersReforms) {
        return helperService.save(teachersReforms);
    }


    @GetMapping("/query")
    public List<Employee> getForTest(@RequestBody structForSelection structForSelection) {
        return this.EmployeeRepository.findByTest(structForSelection.getEmpId(),
                structForSelection.getMossadId(),
                structForSelection.getName(),
                structForSelection.getName(),
                structForSelection.getGender()
        );
    }

    @GetMapping("/test")
    public List<Employee> test(@RequestBody structForSelection structForSelection) {
        return EmployeeRepository.findAll().stream().filter(el -> {
            boolean isValid = true;
            if (structForSelection.getEmpId() != null) {
                isValid = el.getEmpId().equals(structForSelection.getEmpId());
            }

            if (isValid == true && (structForSelection.getGender() == 'F' || structForSelection.getGender() == 'G')) {
                isValid = (el.getGender() == structForSelection.getGender());
            }

            if (isValid == true && structForSelection.getName() != null) {
                isValid = (el.getFirstName().equals(structForSelection.getName()) ||
                        el.getLastName().equals(structForSelection.getName()));
            }

            if (isValid == true && structForSelection.getMinStartDate() != null && structForSelection.getMaxStartDate() != null) {
                isValid = (el.getBegda().after(structForSelection.getMinStartDate()) &&
                        el.getBegda().before(structForSelection.getMaxStartDate())
                );
            }

            if (isValid == true && structForSelection.getMinEndDate() != null && structForSelection.getMaxEndDate() != null) {
                isValid = (el.getEndda().after(structForSelection.getMinEndDate()) &&
                        el.getEndda().before(structForSelection.getMaxEndDate())
                );
            }

            return isValid;

        }).collect(Collectors.toList());
    }

}
