package com.example.biniProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biniProject.Entity.TeacherEmploymentDetails;
import com.example.biniProject.Service.TeacherEmploymentDetailsService;

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
	public float[] getAllWeekHours(@RequestParam(name="tz") String tz) {
		return this.teacherEmploymentDetailsService.getWeek(tz);
	}

	@GetMapping("/byReform")
	public List<TeacherEmploymentDetails> getAllByReformType(@RequestParam(name="tz") String tz,
			@RequestParam(name="mosadId") int mosadId,
			@RequestParam(name="reformType") int reformType){
		
		return teacherEmploymentDetailsService.getAllByReformType(tz, mosadId, reformType);
	}


}
