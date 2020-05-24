package com.example.biniProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class helloWorld {
	
	@GetMapping("/")
	public String hellowWorld() {
		return "hello world";
	}

}
