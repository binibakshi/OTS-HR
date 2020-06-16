package com.example.biniProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biniProject.Entity.Employee;
import com.example.biniProject.Entity.calcHours;
import com.example.biniProject.Repository.CalcHoursRepository;

@Service
public class CalcHoursService {

	@Autowired
	private CalcHoursRepository CalcHoursRepository;
	
	@Autowired
	private EmployeeService employeeService;

	public List<calcHours> findAll(){
		return CalcHoursRepository.findAll();
	}

	public List<calcHours> getEmployeeOptions(int reformType, boolean isMother, int ageHours) {
		return CalcHoursRepository.findAll().stream()
				.filter(record -> record.getReformType() == reformType && 
				record.getAgeHours() == ageHours &&
				record.isMother() == isMother).collect(Collectors.toList());

	}

	public calcHours getByJobPercent(int reformType, boolean isMother, int ageHours, float jobPercent) {
		List<calcHours> calcHours =  CalcHoursRepository.findAll().stream()
				.filter(record -> record.getReformType() == reformType && 
				record.getAgeHours() == ageHours &&
				record.isMother() == isMother &&
				record.getJobPercent() == jobPercent).collect(Collectors.toList());
		if(calcHours.isEmpty()) {
			return null;
		}else {
			return calcHours.get(0);
		}
	}

	public calcHours getByFrontalHours(int reformType, boolean isMother, int ageHours, float frontalHours) {

		List<calcHours> calcHours =  CalcHoursRepository.findAll().stream()
				.filter(record -> record.getReformType() == reformType && 
				record.getAgeHours() == ageHours &&
				record.isMother() == isMother &&
				record.getFrontalHours() == frontalHours).collect(Collectors.toList());
		if(calcHours.isEmpty()) {
			return null;
		}else {
			return calcHours.get(0);
		}
	}
	
	public calcHours getByFrontalTzReformType(String tz, float frontalHours, int reformType ) {
		Employee emp = employeeService.findById(tz);
		return this.getByFrontalHours(reformType, emp.isMother(),employeeService.getAgeHours(emp.getBirthDate()), frontalHours);
	}
	//	public float getJobPercent(int reformType, boolean isMother, int ageHours, float frontalHours) {
	//		return CalcHoursRepository.findAll().stream()
	//				.filter(record -> record.getReformType() == reformType && 
	//				record.getAgeHours() == ageHours &&
	//				record.isMother() == isMother &&
	//				record.getFrontalHours() == frontalHours).collect(Collectors.toList()).get(0).getJobPercent();
	//		
	//	}
}
