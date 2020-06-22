package teachers.biniProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teachers.biniProject.Entity.TeachersReforms;
import teachers.biniProject.Service.helperService;

@CrossOrigin
@RestController
@RequestMapping("/helper")
public class HelperContoller {
	
	@Autowired
	private helperService helperService;
	
	@GetMapping("/all")
	public List<TeachersReforms> getAll() {
		return this.helperService.getAll();
	}
	
	@PostMapping("/save")
	public TeachersReforms save(@RequestBody TeachersReforms teachersReforms) {
		return helperService.save(teachersReforms);
	}
}
