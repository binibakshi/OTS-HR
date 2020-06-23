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

import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Service.TeacherEmploymentDetailsService;

@CrossOrigin
@RestController
@RequestMapping("/teacherEmploymentDetails")
public class TeacherEmploymentDetailsController {

	@Autowired
	private TeacherEmploymentDetailsService teacherEmploymentDetailsService;

	@GetMapping("/all")
	public List<TeacherEmploymentDetails> getAll(){
		return teacherEmploymentDetailsService.findAll();
	}

	@PostMapping("/saveAll")
	public List<TeacherEmploymentDetails> saveAll(@RequestBody List<TeacherEmploymentDetails> teacherEmploymentDetails) {

		return this.teacherEmploymentDetailsService.saveAll(teacherEmploymentDetails);
	}

	@GetMapping("weekSum")
	public float[] getAllWeekHours(@RequestParam(name="empId") String tz) {
		return this.teacherEmploymentDetailsService.getWeek(tz);
	}

	@GetMapping("/byReform")
	public List<TeacherEmploymentDetails> getAllByReformType(@RequestParam(name="empId") String tz,
			@RequestParam(name="mosadId") int mosadId,
			@RequestParam(name="reformType") int reformType){
		
		return teacherEmploymentDetailsService.getAllByReformType(tz, mosadId, reformType);
	}


}