package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.ConvertHours;
import teachers.biniProject.Repository.ConvertHoursRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertHoursService {

    @Autowired
    private ConvertHoursRepository convertHoursRepository;

    public List<ConvertHours> findAll() {
        return this.convertHoursRepository.findAll();
    }

    public int findByCode(int empCode) {
        return this.convertHoursRepository.findById(empCode).get().getReformType();
    }

    public List<ConvertHours> getByReform(int reformType) {
        return this.convertHoursRepository.findAll().stream().
                filter(i -> i.getReformType() == reformType).collect(Collectors.toList());
    }

    public List<Integer> getAllByReform(int reformType) {
        return this.convertHoursRepository.findAll().stream().
                filter(i -> i.getReformType() == reformType).map(i -> i.getCode()).collect(Collectors.toList());
    }

    public List<Integer> getAllFrontalAndReform(int reformType) {
        return this.convertHoursRepository.findAll().stream().
                filter(i -> i.getReformType() == reformType &&
                       i.getReformType() == 1).map(i -> i.getCode()).collect(Collectors.toList());
    }

    // get all the frontal codes
    public List<Integer> getAllFrontal() {
		List<Integer> list = new ArrayList<>();
		for (ConvertHours i : this.convertHoursRepository.findByHourType(1)) {
			Integer code = i.getCode();
			list.add(code);
		}
		return list;
    }

    // get all the codes by the HourType( frontal = 1, private = 2, pause = 3)
    public List<Integer> getHoursByType(int hourType) {
        return this.convertHoursRepository.findAll().
                stream().
                filter(el -> el.getHourType() == hourType).
                map(i -> i.getCode()).
                collect(Collectors.toList());
    }


}
