package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeacherEmploymentDetails;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TeacherEmploymentDetailsRepository extends JpaRepository<TeacherEmploymentDetails, Integer> {
    @Query(value = "select * from teacher_info " +
            "where emp_id = :empId AND " +
            "day = :day AND " +
            "mossad_id = :mossadId AND " +
            "employment_code =:empCode AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    TeacherEmploymentDetails findSingleRecord(@Param("empId") String empId, @Param("day") int day,
                                              @Param("mossadId") int mossadId, @Param("empCode") int empCode,
                                              @Param("begda") Date begda,
                                              @Param("endda") Date endda);

    @Query(value = "select distinct teacher_info.emp_id from teacher_info where mossad_id = :mossadId AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<String> findAllEmpByMossadId(@Param("mossadId") int mossadId,
                                      @Param("begda") Date begda,
                                      @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id = :empId AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByEmpId(@Param("empId") String empId,
                                               @Param("begda") Date begda,
                                               @Param("endda") Date endda);

    @Query(value = "SELECT DISTINCT reform_type from teacher_info where emp_id = :empId AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<Integer> getReformTypeByEmpId(@Param("empId") String empId,
                                       @Param("begda") Date begda,
                                       @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where mossad_id =:mossadId AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByMossadId(@Param("mossadId") int mossadId,
                                                  @Param("begda") Date begda,
                                                  @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id = :empId AND mossad_id =:mossadId AND  " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByEmpIdAndMossadId(@Param("empId") String empId,
                                                          @Param("mossadId") int mossadId,
                                                          @Param("begda") Date begda,
                                                          @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id = :empId AND mossad_id =:mossadId AND " +
            "reform_type =:reformType AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByEmpIdAndMossadIdAndReformType(@Param("empId") String empId,
                                                                       @Param("mossadId") int mossadId,
                                                                       @Param("reformType") int reformType,
                                                                       @Param("begda") Date begda,
                                                                       @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id = :empId AND " +
            "( :mossadId = 0 OR mossad_id = :mossadId ) AND " +
            "( :reformType = 0 OR  reform_type = :reformType ) AND " +
            "( begda <= :endda AND endda >= :begda )",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findOverlapping(@Param("empId") String empId,
                                                   @Param("mossadId") int mossadId,
                                                   @Param("reformType") int reformType,
                                                   @Param("begda") Date begda,
                                                   @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id =:empId AND day =:day AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByEmpIdAndDay(@Param("empId") String empId,
                                                     @Param("day") int day,
                                                     @Param("begda") Date begda,
                                                     @Param("endda") Date endda);

    @Query(value = "select * from teacher_info where emp_id =:empId AND mossad_id =:mossadId AND day =:day AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherEmploymentDetails> findByEmpIdAndMossadIdAndDay(@Param("empId") String empId,
                                                                @Param("mossadId") int mossadId,
                                                                @Param("day") int day,
                                                                @Param("begda") Date begda,
                                                                @Param("endda") Date endda);

    List<TeacherEmploymentDetails> findByBegdaAfterAndEnddaBefore(Date begda, Date endda);

    void deleteByEmpId(String empId);

    @Modifying
    @Transactional
    @Query(value = "delete from teacher_info where emp_id =:empId AND mossad_id =:mossadId AND day =:day AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    void deleteByEmpIdAndMossadId(@Param("empId") String empId,
                                  @Param("mossadId") int mossadId,
                                  @Param("begda") Date begda,
                                  @Param("endda") Date endda);

    @Transactional
    @Modifying
    @Query(value = "delete from teacher_info where emp_id = :empId AND mossad_id = :mossadId AND " +
            "reform_type = :reformType AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    void deleteByEmpIdAndMossadIdAndReformType(@Param("empId") String empId,
                                               @Param("mossadId") int mossadId,
                                               @Param("reformType") int reformType,
                                               @Param("begda") Date begda,
                                               @Param("endda") Date endda);

    @Transactional
    @Modifying
    @Query(value = "delete from teacher_info where emp_id = :empId AND " +
            "( :mossadId = 0 OR mossad_id = :mossadId ) AND " +
            "( :reformType = 0 OR  reform_type = :reformType ) AND " +
            "( begda <= :endda AND endda >= :begda )",
            nativeQuery = true)
    void deleteOverlapps(@Param("empId") String empId,
                         @Param("mossadId") int mossadId,
                         @Param("reformType") int reformType,
                         @Param("begda") Date begda,
                         @Param("endda") Date endda);
}
