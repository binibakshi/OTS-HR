package teachers.biniProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.calcHours;
import teachers.biniProject.Service.CalcHoursService;
import teachers.biniProject.Service.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/calcHours")
public class CalcHoursController {
	
	@Autowired
	private CalcHoursService calcHoursService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/all")
	public List<calcHours> getAll(){
		return calcHoursService.findAll();
	}
	
	@GetMapping("/options")
	public List<calcHours> getEmpOptions(@RequestParam(name="reformType") int reformType, 
										 @RequestParam(name="tz") String tz ){
		
		Employee emp = employeeService.findById(tz);
		
		return this.calcHoursService.getEmployeeOptions(reformType, emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()));
	}
	
	@GetMapping("/byId")
	public calcHours getByFrontal(@RequestParam(name="tz") String tz ,
			@RequestParam(name="reformType") int employmentCode, 
			@RequestParam(name="frontalHours") float frontalHours){

		Employee emp = employeeService.findById(tz);
		return calcHoursService.getByFrontalHours(employmentCode,
				emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()), 
				frontalHours);
		

}
	}
