package com.example.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.biniProject.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
