package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.calcHours;
import teachers.biniProject.Service.CalcHoursService;
import teachers.biniProject.Service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/calcHours")
public class CalcHoursController {

    @Autowired
    private CalcHoursService calcHoursService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public List<calcHours> getAll() {
        return calcHoursService.findAll();
    }

    @GetMapping("/options")
    public List<calcHours> getEmpOptions(@RequestParam(name = "reformType") int reformType,
                                         @RequestParam(name = "empId") String tz) {

        Employee emp = employeeService.findById(tz);

        return this.calcHoursService.getEmployeeByReform(reformType, emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()));
    }

    @GetMapping("/byId")
    public calcHours getByFrontal(@RequestParam(name = "empId") String tz,
                                  @RequestParam(name = "reformType") int employmentCode,
                                  @RequestParam(name = "frontalHours") float frontalHours) {

        Employee emp = employeeService.findById(tz);
        return calcHoursService.getByFrontalHours(employmentCode,
                emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()),
                frontalHours);


    }
}
