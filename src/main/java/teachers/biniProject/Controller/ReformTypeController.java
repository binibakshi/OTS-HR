package teachers.biniProject.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teachers.biniProject.Entity.*;
import teachers.biniProject.Repository.reformTypeRepository;

@CrossOrigin
@RestController
@RequestMapping("/reformTypes")
public class ReformTypeController {
	
	@Autowired
	public reformTypeRepository reformTypeRepository;
	
	
	@GetMapping("/all")
	public List<reformType> getAllReformTypes(){
		return this.reformTypeRepository.findAll();
	}
	
	@GetMapping("/relevant")
	public List<reformType> getRelevantReformTypes(){
		return this.reformTypeRepository.findAll().stream().filter(ref -> ref.getReformId() == 1 || 
															       ref.getReformId() == 2 ||
																   ref.getReformId() == 5 ||
																   ref.getReformId() == 7	 ||
																   ref.getReformId() == 8).collect(Collectors.toList());
	}
}
