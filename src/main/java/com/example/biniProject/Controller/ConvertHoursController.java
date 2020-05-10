package com.example.biniProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biniProject.Entity.convertHours;
import com.example.biniProject.Service.ConvertHoursService;


@CrossOrigin
@RestController
@RequestMapping("/convertHours")
public class ConvertHoursController {
	
	@Autowired
	private ConvertHoursService convertHoursService;
	
	@GetMapping("/all")
	public List<convertHours> getAll(){
		return this.convertHoursService.findAll();
	}
	
	@GetMapping("/byReform")
	public List<convertHours> getByReform(@RequestParam(name="reformType") int reformType){
		return this.convertHoursService.getByReform(reformType);
	}
}
