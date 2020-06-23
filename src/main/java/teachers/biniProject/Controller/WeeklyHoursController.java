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

import teachers.biniProject.Entity.WeeklyHours;
import teachers.biniProject.Service.EmployeeService;
import teachers.biniProject.Service.WeeklyHoursService;

@CrossOrigin
@RestController
@RequestMapping("/weeklyHours")
public class WeeklyHoursController {

	@Autowired
	private WeeklyHoursService weeklyHoursService;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/all")
	public List<WeeklyHours> getAll(){
		return weeklyHoursService.findAll();
	}

	@GetMapping("/byId/{id}")
	public WeeklyHours getById(String tz){
		return weeklyHoursService.findById(tz);
	}

	@PostMapping("/save")
	public WeeklyHours newWeeklyHours(@RequestBody WeeklyHours WeeklyHours) {
		return weeklyHoursService.save(WeeklyHours);
	}

	@PostMapping("/saveAll")
	public List<WeeklyHours> newWeeklyHours(@RequestParam(name="weeklyHours") List<WeeklyHours> weeklyHours,
			@RequestParam(name="tz") String tz ) {

		return weeklyHoursService.saveAll(weeklyHours, tz, this.employeeService.findById(tz).getReform_type());
	}

//	@GetMapping("/leftJobPercent")
//	public float leftJobPercent(@RequestParam(name="id") String tz) {
//		return weeklyHoursService.getLeftJobPercent(tz);
//	}
//
//	@GetMapping("/random")
//	public List<WeeklyHours> randomizeHours(@RequestParam(name="tz") String tz ,
//			@RequestParam(name="reformType") int reformType, 
//			@RequestParam(name="frontalHours") float frontalHours){
//
//		Employee emp = employeeService.findById(tz);
//		calcHours calcHours = calcHoursService.getByFrontalHours(reformType,
//				emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()), 
//				frontalHours);
//
//
//
//		return weeklyHoursService.RandomizeHours(tz, calcHours.getFrontalHours(), 
//				calcHours.getPrivateHours(), calcHours.getPauseHours());
//	}

//	@GetMapping("/random2")
//	public List<WeeklyHours> randomizeHours2(@RequestParam(name="tz") String tz ,
//			@RequestParam(name="employmentCode") int employmentCode, 
//			@RequestParam(name="frontalHours") float frontalHours){
//
//		Employee emp = employeeService.findById(tz);
//		calcHours calcHours = calcHoursService.getByFrontalHours(convertHoursService.findByCode(employmentCode),
//				emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()), 
//				frontalHours);
//
//
//
//		return weeklyHoursService.RandomizeHours(tz, calcHours.getFrontalHours(), 
//				calcHours.getPrivateHours(), calcHours.getPauseHours());
//	}

}