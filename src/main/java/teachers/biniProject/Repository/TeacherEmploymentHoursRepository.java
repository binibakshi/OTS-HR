package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.TeacherEmploymentHours;

public interface TeacherEmploymentHoursRepository extends JpaRepository<TeacherEmploymentHours, Integer> {
}
