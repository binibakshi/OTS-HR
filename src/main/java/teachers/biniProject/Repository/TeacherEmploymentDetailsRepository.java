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

    @Query(value = "select distinct teacher_info.emp_id from teacher_info where mossad_id = :mossadId",
            nativeQuery = true)
    List<String> findAllEmpByMossadId(@Param("mossadId") int mossadId);

    List<TeacherEmploymentDetails> findByEmpId(String empId);

    List<TeacherEmploymentDetails> findByMossadId(int mossadId);

    List<TeacherEmploymentDetails> findByEmpIdAndMossadId(String empId, int mossadId);

    List<TeacherEmploymentDetails> findByEmpIdAndMossadIdAndReformType(String empId, int mossadId, int reformType);

    List<TeacherEmploymentDetails> findByEmpIdAndDay(String empId, int day);

    List<TeacherEmploymentDetails> findByEmpIdAndMossadIdAndDay(String empId, int mossadId, int day);

    void deleteByEmpId(String empId);

    void deleteByEmpIdAndMossadId(String empId, int mossadId);

    void deleteByEmpIdAndMossadIdAndReformType(String empId, int mossadId, int reformType);


}
