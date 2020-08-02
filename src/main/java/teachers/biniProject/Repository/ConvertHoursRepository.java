package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.convertHours;

import java.util.List;

public interface ConvertHoursRepository extends JpaRepository<convertHours, Integer>{

    List<convertHours> findByHourType(int hourType);
}
