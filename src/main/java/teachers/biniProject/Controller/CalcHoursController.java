package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.CalcHours;
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
    public List<CalcHours> getAll() {
        return calcHoursService.findAll();
    }

    @GetMapping("/options")
    public List<CalcHours> getEmpOptions(@RequestParam(name = "reformType") int reformType,
                                         @RequestParam(name = "empId") String tz,
                                         @RequestParam(name = "year") int year) {

        Employee emp = employeeService.findById(tz);

        return this.calcHoursService.getEmployeeByReform(reformType, emp.isMother(),
                employeeService.getAgeHours(emp.getBirthDate(),year));
    }
}
