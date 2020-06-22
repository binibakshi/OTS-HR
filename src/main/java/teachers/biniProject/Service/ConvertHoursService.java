package teachers.biniProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teachers.biniProject.Entity.convertHours;
import teachers.biniProject.Repository.ConvertHoursRepository;

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
	
	// get all the frontal codes
	public List<Integer> getAllFrontal(){
		return this.convertHoursRepository.findAll().
										   stream().
										   filter(el -> el.getHourType() == 1).
										   map(i -> i.getCode()).
										   collect(Collectors.toList());
	}
	// get all the codes by the HourType( frontal = 1, private = 2, pause = 3)
		public List<Integer> getHoursByType(int hourType){
			return this.convertHoursRepository.findAll().
											   stream().
											   filter(el -> el.getHourType() == hourType).
											   map(i -> i.getCode()).
											   collect(Collectors.toList());
		}
		

}
