package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.calcHours;

import java.util.List;

public interface CalcHoursRepository extends JpaRepository<calcHours, Integer> {

    @Query(value = "select * " +
            "from calc_hours " +
            "where ( is_mother = :isMother AND " +
            "age_hours = :ageHours AND " +
            "reform_type = :reformType )",
            nativeQuery = true)
    List<calcHours> option(@Param("isMother") boolean isMother,
                           @Param("ageHours") int ageHours,
                           @Param("reformType") int reformType);
}
