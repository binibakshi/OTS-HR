package teachers.biniProject.Controller;


import java.util.Date;
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
	public Employee getEmployee(@RequestParam(name="empId") String id) {
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
	
	@GetMapping("/byAnyName")
	public List<Employee> getByAnyName(@RequestParam(name="name") String name){
		return this.employeeService.getByFirstName(name);
	}
	
	@GetMapping("/byFullName")
	public List<Employee> getByFullName(@RequestParam(name="firstName") String firstName,
			 							 @RequestParam(name="lastName") String lastName){
		return this.employeeService.getByFullName(firstName, lastName);
	}
	
	@GetMapping("/byIsMother")
	public List<Employee> getByIsmother(@RequestParam(name="isMother") Boolean isMother){
		return this.employeeService.getByIsmother(isMother);
	}
	
	@GetMapping("/bySchoolYear")
	public List<Employee> getByYear(@RequestParam(name="year") int currYear){

		return this.employeeService.getByYear(currYear);
	}
	
	@GetMapping("/byGender")
	public List<Employee> getByGender(@RequestParam(name="gender") char gender  ){

		return this.employeeService.getByGender(gender);
	}
	
	@GetMapping("/byStrartDate")
	public List<Employee> getByStratDates(@RequestParam(name="minStartDate") Date startDate,
									  @RequestParam(name="maxStartDate") Date endDate
										){

		return this.employeeService.getByStratDates(startDate, endDate);
	}
	
	@GetMapping("/byEndDate")
	public List<Employee> getByEndDates(@RequestParam(name="minEnndDate") Date startDate,
									  @RequestParam(name="maxEndDate") Date endDate
										){

		return this.employeeService.getByStratDates(startDate, endDate);
	}
	
	@GetMapping("/byMossad")
	public List<Employee> getMossad(@RequestParam(name="year") int mossadId){

		return this.employeeService.getMossad(mossadId);
	}
	
	
	
		
	

}
