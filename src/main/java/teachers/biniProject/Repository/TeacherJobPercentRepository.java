package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.TeacherJobPercent;
import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;

import java.util.List;

public interface TeacherJobPercentRepository extends JpaRepository<TeacherJobPercent, EmpIdYearComositeKey> {

    List<TeacherJobPercent> findAllByYear(int year);

    List<TeacherJobPercent> findAllByYearAndMossadId(int year, int mossadId);

}
