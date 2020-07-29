package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeacherEmploymentDetails;

import java.util.List;

public interface TeacherEmploymentDetailsRepository extends JpaRepository<TeacherEmploymentDetails, Integer> {
    @Query(value = "select * from teacher_info " +
            "where emp_id = :empId AND " +
            "day = :day AND " +
            "mossad_id = :mossadId AND " +
            "employment_code =:empCode ",
            nativeQuery = true)
    TeacherEmploymentDetails findSingleRecord(@Param("empId") String empId, @Param("day") int day,
                                              @Param("mossadId") int mossadId, @Param("empCode") int empCode);

    List<TeacherEmploymentDetails> findByEmpId(String empId);

    List<TeacherEmploymentDetails> findByEmpIdAndMossadId(String empId, int mossadId);

    List<TeacherEmploymentDetails> findByEmpIdAndDay(String empId, int day);

    List<TeacherEmploymentDetails> findByEmpIdAndMossadIdAndDay(String empId, int mossadId, int day);

    void deleteByEmpId(String empId);

    void deleteByEmpIdAndMossadId(String empId, int mossadId);

}
