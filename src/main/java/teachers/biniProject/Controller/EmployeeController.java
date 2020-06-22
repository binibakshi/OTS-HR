package teachers.biniProject.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Service.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(path="/all")
	public List<Employee> getAllEmployees(){
		return employeeService.findAll();
	}
	
	@GetMapping("/byId")
	public Employee getEmployee(@RequestParam(name="tz") String id) {
//		if (employeeService.findById(id) == null) {
//			throw new DataNotFoundExeption("employee with " + id + "not found");
//		}
		return employeeService.findById(id);
	}
	
	@PostMapping("/save")
	public Employee newEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	
	@PostMapping("/saveAll")
	public List<Employee> newEmployee(@RequestBody List<Employee> employees) {
		return employeeService.saveAll(employees);
	}
	
	@GetMapping("/byFirstName")
	public List<Employee> getByFirstName(@RequestParam(name="firstName") String firstName){
		return this.employeeService.getByFirstName(firstName);
	}
	
	@GetMapping("/byLastName")
	public List<Employee> getByLastName(@RequestParam(name="lastName") String lastName){
		return this.employeeService.getByFirstName(lastName);
	}
	
	@GetMapping("/byFullName")
	public List<Employee> getByFullName(@RequestParam(name="firstName") String firstName,
			 							 @RequestParam(name="lastName") String lastName){
		return this.employeeService.getByFullName(firstName, lastName);
	}
	
		
	

}
