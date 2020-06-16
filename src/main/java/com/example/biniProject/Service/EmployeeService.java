package com.example.biniProject.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biniProject.Entity.Employee;
import com.example.biniProject.Entity.WeeklyHours;
import com.example.biniProject.Exeption.DataNotFoundExeption;
import com.example.biniProject.Repository.EmployeeRepository;
import com.example.biniProject.Repository.reformTypeRepository;
import com.example.biniProject.Repository.WeeklyHoursRepository;;


@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository emplyeeReposiroty;

	@Autowired
	private WeeklyHoursService weeklyHoursService;

	@Autowired
	private WeeklyHoursRepository weeklyHoursRepository;

	@Autowired
	private reformTypeRepository reformTypeRepository;

	public Employee save(Employee employee) {
		Calendar testDate = Calendar.getInstance();
		employee.setBegda(testDate.getTime());
		testDate.set(9999, Calendar.DECEMBER, 31);
		employee.setEndda(testDate.getTime());
		return emplyeeReposiroty.save(employee);
	}

	public List<Employee> saveAll(List<Employee> employees){
		return emplyeeReposiroty.saveAll(employees);
	}

	public List<Employee> findAll(){
		return emplyeeReposiroty.findAll();
	}
	
	public int getAgeHours(Date birthdate) {
		@SuppressWarnings("deprecation")
		long yearsDiff = ChronoUnit.YEARS.between(
				LocalDate.of( birthdate.getYear() , 12 , 31 ) 
				, LocalDate.now());
		if(yearsDiff > 55) {
			return 4;
		}else if(yearsDiff < 50) {
			return 0;
		}else {
			return 2;
		}
	}

	public Employee findById(String id) {
//		return emplyeeReposiroty.findById(id).orElse(null);
		return emplyeeReposiroty.findById(id).orElseThrow(() -> new DataNotFoundExeption(id));
	}

	public Employee updateStatus(Employee employee) {
		char status = employee.getStatus();

		// check if there is change at all
		if(emplyeeReposiroty.findById(employee.getId()).equals(status)) {
			return null;
		}

		if(status == 'A') {
			this.onApprovedEmployee(employee);
			return emplyeeReposiroty.save(employee);
		}
		else if(status == 'D'){
			this.onDenyEmployee(employee.getId());
			return emplyeeReposiroty.save(employee);
		}else {
			return null;
		}

	}

	public float getMaxJobPercent(String tz) {
		return reformTypeRepository.findById(
				emplyeeReposiroty.findById(tz).get().getReform_type()).
				get().getmaxJobPercent();

	}

	private void onApprovedEmployee(Employee employee) {

		// if for some reason there is already record in so don't create new
		if(weeklyHoursService.findAllByTz(employee.getId()).isEmpty() == false) {
			return;
		}

		// create 6 empty records for each day in week
		for(int i = 1; i < 7; i++) {
			weeklyHoursRepository.save(new WeeklyHours(employee.getId(), 
					employee.getBegda(), employee.getEndda(), 0, 
					0, i, 0,
					0, 0, employee.getCreateBy(), 0));
		}
	}

	private void onDenyEmployee(String tz) {
		List<WeeklyHours> weeklyHoursToDelete = weeklyHoursService.findAllByTz(tz);

		weeklyHoursRepository.deleteAll(weeklyHoursToDelete);
	}
}
