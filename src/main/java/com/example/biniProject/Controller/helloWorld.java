package com.example.biniProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloWorld {
	
	@GetMapping("/")
	public String hellowWorld() {
		return "hello world";
	}

}
