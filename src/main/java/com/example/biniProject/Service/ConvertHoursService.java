package com.example.biniProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biniProject.Entity.convertHours;
import com.example.biniProject.Repository.ConvertHoursRepository;

@Service
public class ConvertHoursService {

	@Autowired
	private ConvertHoursRepository convertHoursRepository;

	public List<convertHours> findAll(){
		return this.convertHoursRepository.findAll();
	}

	public int findByCode(int empCode) {
		return this.convertHoursRepository.findById(empCode).get().getReformType();
	}

	public List<convertHours> getByReform(int reformType){
		return this.convertHoursRepository.findAll().stream().
				filter(i -> i.getReformType() == reformType).collect(Collectors.toList());
	}

	public List<Integer> getAllByReform(int reformType){
		return this.convertHoursRepository.findAll().stream().
				filter(i -> i.getReformType() == reformType ).map(i -> i.getCode()).collect(Collectors.toList());
	}

}
