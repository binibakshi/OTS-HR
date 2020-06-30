package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeacherEmploymentDetails;

public interface TeacherEmploymentDetailsRepository extends JpaRepository<TeacherEmploymentDetails, Integer> {

    @Query(value = "select distinct * " +
            "from teachers_info " +
            "where emp_id = :empId AND " +
            "day = :day AND " +
            "mossad_id = :mossadId AND " +
            "emp_code = :empCode ",
    nativeQuery = true)
    TeacherEmploymentDetails findSingleRecord(@Param("empId") String empId,
                                              @Param("day") int day,
                                              @Param("mossadId") int mossadId,
                                              @Param("empCode") int empCode);
}
