package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.TeacherJobPercent;
import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;
import teachers.biniProject.Repository.TeacherJobPercentRepository;

import java.util.List;

@Service
public class TeacherJobPercentService {

    @Autowired
    TeacherJobPercentRepository teacherJobPercentRepository;


    public List<TeacherJobPercent> getAll() {
        return this.teacherJobPercentRepository.findAll();
    }

    public List<TeacherJobPercent> getAllByYear(int year) {
        return this.teacherJobPercentRepository.findAllByYear(year);
    }

    public List<TeacherJobPercent> getAllByYearAndMossadId(int year, int mossadId) {
        return this.teacherJobPercentRepository.findAllByYearAndMossadId(year, mossadId);
    }

    public TeacherJobPercent getById(EmpIdYearComositeKey empIdYearComositeKey) {
        return this.teacherJobPercentRepository.findById(empIdYearComositeKey).orElse(null);
    }


    public TeacherJobPercent save(TeacherJobPercent teacherJobPercent) {
        EmpIdYearComositeKey empIdYearComositeKey = new EmpIdYearComositeKey(teacherJobPercent.getEmpId(),
                teacherJobPercent.getMossadId(), teacherJobPercent.getYear());
        if (teacherJobPercent.getJobPercent() == 0) {
            if (this.teacherJobPercentRepository.existsById(empIdYearComositeKey)) {
                this.teacherJobPercentRepository.deleteById(empIdYearComositeKey);
            }
        }
        return this.teacherJobPercentRepository.save(teacherJobPercent);
    }

    public List<TeacherJobPercent> saveAll(List<TeacherJobPercent> teacherJobPercent) {
        return this.teacherJobPercentRepository.saveAll(teacherJobPercent);
    }
}
