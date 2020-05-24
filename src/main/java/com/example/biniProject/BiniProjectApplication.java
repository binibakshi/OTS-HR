package com.example.biniProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class BiniProjectApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BiniProjectApplication.class, args);
	}

}
