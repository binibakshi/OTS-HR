package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeacherEmploymentHours;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TeacherEmploymentHoursRepository extends JpaRepository<TeacherEmploymentHours, Integer> {


    @Transactional
    @Modifying
    @Query(value = "update TEACHER_HOURS " +
            "set HOURS = :hours " +
            "where empemp_id = :empId AND " +
            "( :mossadId = 0 OR mossad_id = :mossadId ) AND " +
            "( :reformType = 0 OR  reform_type = :reformType ) AND " +
            "( begda <= :endda AND endda >= :begda )",
            nativeQuery = true)
    void updateByEmpCode(@Param("empId") String empId,
                                           @Param("mossadId") int mossadId,
                                           @Param("reformType") int reformType,
                                           @Param("begda") Date begda,
                                           @Param("endda") Date endda,
                                           @Param("hours") int hours);

    @Transactional
    @Modifying
    @Query(value = "delete from TEACHER_HOURS where emp_id = :empId AND " +
            "( :mossadId = 0 OR mossad_id = :mossadId ) AND " +
            "( :reformType = 0 OR  reform_type = :reformType ) AND " +
            "( begda <= :endda AND endda >= :begda )",
            nativeQuery = true)
    void deleteOverlapps(@Param("empId") String empId,
                         @Param("mossadId") int mossadId,
                         @Param("reformType") int reformType,
                         @Param("begda") Date begda,
                         @Param("endda") Date endda);


    @Transactional
    @Modifying
    @Query(value = "delete from TEACHER_HOURS where emp_id = :empId AND " +
            "( :mossadId = 0 OR mossad_id = :mossadId ) AND " +
            "( :reformType = 0 OR  reform_type = :reformType ) AND " +
            "( begda <= :endda AND endda >= :begda ) AND " +
            "( emp_code not in :empCodes )",
            nativeQuery = true)
    void deleteOverlappsByEmpCodes(@Param("empId") String empId,
                                   @Param("mossadId") int mossadId,
                                   @Param("reformType") int reformType,
                                   @Param("begda") Date begda,
                                   @Param("endda") Date endda,
                                   @Param("empCodes") List<Integer> empCodes);
}
