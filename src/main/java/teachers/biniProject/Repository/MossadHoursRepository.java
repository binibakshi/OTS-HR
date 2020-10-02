package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.MossadHours;
import teachers.biniProject.HelperClasses.MossadHoursComositeKey;

import java.util.List;

public interface MossadHoursRepository extends JpaRepository<MossadHours, MossadHoursComositeKey> {

    List<MossadHours> findAllByYear(int year);
}
