package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.TeachersReforms;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Repository.TeacherReformsRepository;

import java.util.List;

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


}
