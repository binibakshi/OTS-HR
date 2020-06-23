package teachers.biniProject.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teachers.biniProject.Entity.TeachersReforms;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Repository.TeacherReformsRepository;

@Service
public class helperService {

	@Autowired
	private TeacherReformsRepository teacherReformsRepository;

	public List<TeachersReforms> getAll(){
		return this.teacherReformsRepository.findAll();
	}

	public TeachersReforms save(TeachersReforms teachersReforms) {

		if (this.teacherReformsRepository.findAll().stream().
				filter(el ->el.getEmpId().equals(teachersReforms.getEmpId())).count() > 1) {
			throw new GenericException("אי אפשר להזין  יותר מ2  רפורמות לעובד");
		}
		return teacherReformsRepository.save(teachersReforms);
	}

	public float maxJobPercentById(String tz) {
		List<Integer> cuReforms = this.teacherReformsRepository.findAll().stream().
				filter(el -> el.getEmpId().equals(tz)).
				map(el -> el.getReformType()).
				collect(Collectors.toList());

		if (cuReforms.contains(2) || cuReforms.contains(3) || cuReforms.contains(4) ) {
			return 117;

		} else if (cuReforms.contains(5) || cuReforms.contains(6) ) {
			return 125;

		} else if (cuReforms.contains(1) || cuReforms.contains(7)) {
			return 140;
		}
		else {
			return 100;
		}
	}
}