package com.example.biniProject.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biniProject.Entity.TeacherEmploymentDetails;
import com.example.biniProject.Entity.calcHours;
import com.example.biniProject.Exeption.GenericException;
import com.example.biniProject.Repository.TeacherEmploymentDetailsRepository;

@Service
public class TeacherEmploymentDetailsService {

	private final float MAX_HOURS_PER_DAY = 9;
	private final float MAX_HOURS_FRIDAY = 6;

	@Autowired
	private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

	@Autowired
	private ConvertHoursService convertHoursService;

	@Autowired
	private CalcHoursService calcHourService;

	@Autowired
	private helperService helperService;


	public List<TeacherEmploymentDetails> findAll() {
		return teacherEmploymentDetailsRepository.findAll();
	}

	public TeacherEmploymentDetails findById(int id) {
		return teacherEmploymentDetailsRepository.findById(id).orElse(null);
	}

	public TeacherEmploymentDetails save(TeacherEmploymentDetails teacherEmploymentDetails){
		Calendar calendar = Calendar.getInstance();
		teacherEmploymentDetails.setBegda(calendar.getTime());
		calendar.set(Calendar.getInstance().get(Calendar.YEAR + 1), Calendar.DECEMBER, 31);
		teacherEmploymentDetails.setEndda(calendar.getTime());
		return teacherEmploymentDetailsRepository.save(teacherEmploymentDetails);
	}

	public List<TeacherEmploymentDetails> saveAll(List<TeacherEmploymentDetails> teacherEmploymentDetails){

		// check validation raise exception if needed
		this.checkNewHoursValidation(teacherEmploymentDetails);

		this.setRecordForSave(teacherEmploymentDetails);
		teacherEmploymentDetails.forEach(i -> {
			// check if the record not exist yet
			if(this.updateIfExist(i) != true) {
				if(i.getHours() != 0) {
					teacherEmploymentDetailsRepository.save(i);	
				}
			}
		});
		return teacherEmploymentDetails;
	}
	
	// validate checks raise exceptions if needed
	public void checkNewHoursValidation(List<TeacherEmploymentDetails> teacherEmploymentDetails)  {		
		String tz;

		if(teacherEmploymentDetails.isEmpty()) {
			// ????????????
			return ;
		}

		tz = teacherEmploymentDetails.get(0).getTz();
		this.checkMaxHoursInDay(teacherEmploymentDetails, tz);
		this.checkMaxJobPercent(teacherEmploymentDetails, tz);

	}

	private void checkMaxHoursInDay(List<TeacherEmploymentDetails> teacherEmploymentDetails, String tz) {
		float[] week = new float[6];
		for(int j = 0; j< week.length; j++) {
			week[j]= 0; 
		}

		this.getWeeklyHours(week, tz);

		// TODO Auto-generated method stub
		for(int i = 0; i < teacherEmploymentDetails.size(); i++) {

			week[teacherEmploymentDetails.get(i).getDay()] += teacherEmploymentDetails.get(i).getHours();

			if(week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_PER_DAY ||
					( teacherEmploymentDetails.get(i).getDay() == 5 && 
					week[teacherEmploymentDetails.get(i).getDay()] > MAX_HOURS_FRIDAY )
					) {
				throw new GenericException("אי אפשר להזין יותר מ" + MAX_HOURS_PER_DAY + " שעות ביום");
				//				return false;
			}

		}
	}

	public void setRecordForSave(List<TeacherEmploymentDetails> teacherEmploymentDetails) {


		Date todayDate = new Date();
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MONTH, 11); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

		teacherEmploymentDetails.forEach(el -> {
			el.setBegda(todayDate);
			el.setEndda(cal.getTime());
		});
	}

	public void getWeeklyHours(float[] week, String tz) {
		this.teacherEmploymentDetailsRepository.findAll().stream().filter(obj -> obj.getTz().equals(tz)).forEach(el -> {
			week[el.getDay()] += el.getHours();
		});
	}

	public List<TeacherEmploymentDetails> getEmpDay(String tz, int day) {
		return this.teacherEmploymentDetailsRepository.findAll().stream().
				filter(i -> i.getTz().equals(tz) &&
						i.getDay() == day).collect(Collectors.toList());
	}

	public float getExistHours(String tz) {
		return (float)this.teacherEmploymentDetailsRepository.findAll().stream().
				filter(i -> i.getTz().equals(tz)).mapToDouble(i -> i.getHours()).sum();
	}

	public float[] getWeek(String tz){
		float[] week = new float[6];
		for(int i = 0; i < 6; i++) {
			week[i]= (float)this.getEmpDay(tz, i).stream().mapToDouble(el -> el.getHours()).sum(); 
		}
		return week;
	}

	public boolean updateIfExist(TeacherEmploymentDetails teacherEmploymentDetails) {

		TeacherEmploymentDetails temp =  new TeacherEmploymentDetails(teacherEmploymentDetailsRepository.findAll().stream().
				filter(i->i.getTz().equals(teacherEmploymentDetails.getTz()) &&
						i.getMosadId() == teacherEmploymentDetails.getMosadId()  &&
						i.getDay() == teacherEmploymentDetails.getDay() &&
						i.getEmpCode() == teacherEmploymentDetails.getEmpCode()).findFirst().orElse(null));

		if(temp.getTz() == null) {
			return false;
		}
		if(temp.getHours() == teacherEmploymentDetails.getHours()) {
			return true;
		}

		if(temp.getHours() != teacherEmploymentDetails.getHours()) {
			temp.setHours(teacherEmploymentDetails.getHours());
			if(temp.getHours() == 0) {
				this.teacherEmploymentDetailsRepository.delete(temp);
				return true;
			} else {
				this.teacherEmploymentDetailsRepository.save(temp);	
				return true;
			}
		}

		return false;

	}

	public List<TeacherEmploymentDetails> getAllByReformType(String tz, int mosadId, int reformType){

		return this.teacherEmploymentDetailsRepository.findAll().
				stream().
				filter(i-> i.getTz().equals(tz) &&
						i.getMosadId() == mosadId &&
						convertHoursService.getAllByReform(reformType).
						contains(i.getEmpCode())
						).
				collect(Collectors.toList());

	}

	// check for max job percent 
	private void checkMaxJobPercent(List<TeacherEmploymentDetails> employmentDetails, String tz) {
		int currReformType;
		float maxJobPercet = 0;
		float frontalHours = 0;
		calcHours calcHours;

		if (employmentDetails.size() <= 0) {
			return;
		}

		maxJobPercet = helperService.maxJobPercentById(tz);

		currReformType = this.convertHoursService.findByCode(employmentDetails.get(0).getEmpCode());

		// get the current codes (to not loop at them...)
		final List<Integer> currCodes = employmentDetails.stream().
				filter(el -> this.convertHoursService.getAllByReform(currReformType).contains(el.getEmpCode()) == true ).
				map(i -> i.getEmpCode()).distinct().
				collect(Collectors.toList());

		// get the current frontal hours of the sent reform type (always send from the client for each change)
		frontalHours += employmentDetails.stream().
				filter(el -> this.convertHoursService.getAllByReform(currReformType).contains(el.getEmpCode()) == true ).
				mapToDouble(i -> i.getHours()).
				sum();

		// get the other exist codes
		frontalHours +=  this.teacherEmploymentDetailsRepository.findAll().
				stream().filter(el -> el.getTz().equals(tz) &&
				this.convertHoursService.getAllByReform(currReformType).contains(el.getEmpCode()) == true &&
				currCodes.contains(el.getEmpCode()) == false ).
				mapToDouble(i -> i.getHours()).
				sum();

		calcHours = this.calcHourService.getByFrontalTzReformType(tz, frontalHours, currReformType);

		if (calcHours.getJobPercent() > maxJobPercet) {
			throw new GenericException("לעובד יש מגבלת מגבלת אחוז משרה של " + maxJobPercet + "כעת יש %" + calcHours.getJobPercent() +"%");
		}

	}

}
