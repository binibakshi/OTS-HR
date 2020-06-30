package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.calcHours;
import teachers.biniProject.Repository.CalcHoursRepository;

import java.util.List;
import java.util.stream.Collectors;

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

		return CalcHoursRepository.option(isMother, ageHours, reformType);

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
