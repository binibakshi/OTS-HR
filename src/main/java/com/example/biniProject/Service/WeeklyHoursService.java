package com.example.biniProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biniProject.Entity.WeeklyHours;
import com.example.biniProject.Repository.WeeklyHoursRepository;
import com.example.biniProject.Repository.reformTypeRepository;

@Service
public class WeeklyHoursService {
	
//	private final float MAX_HOURS_IN_DAY = (float)9;
	private final float MIN_JOB_PERCENT = (float)33.3;
	
	@Autowired
	private WeeklyHoursRepository weeklyHoursRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private reformTypeRepository reformTypeRepository;

	public List<WeeklyHours> findAll(){
		return weeklyHoursRepository.findAll();
	}

	public WeeklyHours findById(String tz){
		return weeklyHoursRepository.findById(tz).orElse(null);
	}

	public List<WeeklyHours> saveAll(List<WeeklyHours> weeklyHours, String tz, int reformType){
		if(this.saveValidation(weeklyHours, reformType, tz) == false) {
			return null;
		}
		return weeklyHoursRepository.saveAll(weeklyHours);
	}

	public WeeklyHours save(WeeklyHours weeklyHours){
		return weeklyHoursRepository.save(weeklyHours);
	}

	public List<WeeklyHours> findAllByTz(String tz){
		return weeklyHoursRepository.findAll().stream()
				.filter(dayHours -> dayHours.getTz().equals(tz)).collect(Collectors.toList());
	}
	
	public float getLeftJobPercent(String tz) {
		return employeeService.getMaxJobPercent(tz) - 
				(float)this.findAllByTz(tz).stream().mapToDouble(i -> i.getJobPercent()).sum(); 
	}

//	public List<WeeklyHours> RandomizeHours(String tz, float frontalHours, float privateHours, float pauseHours){
//		WeeklyHours currentDay = new WeeklyHours();
//		int randomDay;
//		float randomHours, currentHours;
//		WeeklyHours[] weeklyHoursArray = new WeeklyHours[6];
//		
//		// initilize the array
//		for(int i = 0; i < 6 ; i++) {
//			weeklyHoursArray[i] = new WeeklyHours();
//			weeklyHoursArray[i].setDay(i);
//		}
//		
//		// if there is existing records take them
//		this.getExistHours(tz, weeklyHoursArray);
//
//		// go over and radomize day with random hours until there is no more hours left
//		while(frontalHours > 0 || privateHours > 0 || pauseHours > 0 ) {
//			randomDay = ThreadLocalRandom.current().nextInt(0, 6);
//			currentDay = weeklyHoursArray[randomDay];
//			currentHours = currentDay.getFrontalHours() +
//					       currentDay.getPrivateHours() +
//			       		   currentDay.getPauseHours();
//
//			if(currentHours < MAX_HOURS_IN_DAY && frontalHours > 0) {
//				randomHours = this.getRandomHours(currentHours, frontalHours, randomDay);
//				currentDay.setFrontalHours(currentDay.getFrontalHours() + randomHours);
//				currentHours += randomHours;
//				frontalHours -= randomHours;
//			}
//			
//			if(currentHours < MAX_HOURS_IN_DAY && privateHours > 0) {
//				randomHours = this.getRandomHours(currentHours, privateHours, randomDay);
//				currentDay.setPrivateHours(currentDay.getPrivateHours() + randomHours);
//				currentHours += randomHours;
//				privateHours -= randomHours;
//			}
//			
//			if(currentHours < MAX_HOURS_IN_DAY && pauseHours > 0) {
//				randomHours = this.getRandomHours(currentHours, pauseHours, randomDay);
//				currentDay.setPauseHours(currentDay.getPauseHours() + randomHours);
//				currentHours += randomHours;
//				pauseHours -= randomHours;
//			}
//
//		}
//		return List.of(weeklyHoursArray);
//	}
	
//	public List<WeeklyHours> RandomizeHours2(String tz, int[] days,  float frontalHours, float privateHours, float pauseHours){
//		WeeklyHours currentDay = new WeeklyHours();
//		int randomDay;
//		float randomHours, currentHours;
//		WeeklyHours[] weeklyHoursArray = new WeeklyHours[6];
//		
//		// initilize the array
//		for(int i = 0; i < 6 ; i++) {
//			weeklyHoursArray[i] = new WeeklyHours();
//			weeklyHoursArray[i].setDay(i);
//		}
//		
//		// if there is existing records take them
//		this.getExistHours(tz, weeklyHoursArray);
//		
//		return null;
//		
//		for(int i = 0; i < 6 ; i++) {
//			while(days[i] < )
//		}
//	}
	
	
//	private void getExistHours(String tz, WeeklyHours[] weeklyHours) {
//		this.findAllByTz(tz).stream().forEach(Day -> {
//			weeklyHours[Day.getDay() - 1] = Day;
//		});
//		return;
//	}
	
	// randomize hours between 0 to max hours to append(max is 8 in a day)
//	private float getRandomHours(float currentHours, float hoursToSub, int day) {
//		float maxHoursInDay = MAX_HOURS_IN_DAY - currentHours;
//		if(day == 5) {
//			maxHoursInDay = 6 - currentHours;
//		}
//		if(hoursToSub == 0.5) {
//			return (float)0.5;
//		}
//		int maxHours = Math.min((int)maxHoursInDay, (int)hoursToSub);
//		return (float)(ThreadLocalRandom.current().nextInt(0, maxHours + 1 ));
//	}
	
	public boolean saveValidation(List<WeeklyHours> weeklyHours, int reformType, String tz) {
		float maxJobPercent = reformTypeRepository.findById(reformType).get().getmaxJobPercent();
		float currJobPercent;
		boolean isValid = false;
		
		if(weeklyHours.isEmpty() == false) {
			currJobPercent = (float)weeklyHours.stream().mapToDouble(i -> i.getJobPercent()).sum() +
					         (float)weeklyHoursRepository.findAll().stream().filter(el ->el.getTz() == tz).
					         mapToDouble(i -> i.getJobPercent()).sum();
			
			if(currJobPercent > maxJobPercent) {
				//TODO raise exeption				
			} else if(currJobPercent < MIN_JOB_PERCENT) {
				//TODO reise exeption
			}else {
				isValid = true;
			}
		}
		return isValid;
	}
	
	

}
