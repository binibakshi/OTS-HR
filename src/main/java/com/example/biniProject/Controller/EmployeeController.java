package com.example.biniProject.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.biniProject.Entity.Employee;
import com.example.biniProject.Service.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/hello")
	public @ResponseBody String hello() {
		return "hello world";
	}
	
	@GetMapping(path="/all")
	public List<Employee> getAllEmployees(){
		return employeeService.findAll();
	}
	
	@GetMapping("/byId")
	public Employee getEmployee(@RequestParam(name="id") String id) {
		return employeeService.findById(id);
	}
	
	@PostMapping("/new")
	public Employee newEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	
	@PostMapping("/newSet")
	public List<Employee> newEmployee(@RequestBody List<Employee> employees) {
		return employeeService.saveAll(employees);
	}
		
	

}
