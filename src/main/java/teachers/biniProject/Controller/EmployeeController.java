package teachers.biniProject.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Service.EmployeeService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/all")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/byId")
    public Employee getEmployee(@RequestParam(name = "empId") String id) {
        return employeeService.findById(id);
    }

    @DeleteMapping("/byId")
    public void deleteEmployee(@RequestParam(name = "empId") String empId) {
        this.employeeService.deleteByEmpId(empId);
    }

    @PostMapping("/save")
    public Employee newEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/saveAll")
    public List<Employee> newEmployee(@RequestBody List<Employee> employees) {
        return employeeService.saveAll(employees);
    }

    @GetMapping("/byMossad")
    public List<Employee> getMossad(@RequestParam(name = "mossadId") int mossadId,
                                    @RequestParam(name = "begda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begda,
                                    @RequestParam(name = "endda") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endda) {
        return this.employeeService.getAllEmpInMossad(mossadId, begda, endda);
    }

}
