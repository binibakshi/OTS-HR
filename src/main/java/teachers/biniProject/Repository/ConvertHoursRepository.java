package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.ConvertHours;

import java.util.List;

public interface ConvertHoursRepository extends JpaRepository<ConvertHours, Integer>{

    List<ConvertHours> findByHourType(int hourType);
}
