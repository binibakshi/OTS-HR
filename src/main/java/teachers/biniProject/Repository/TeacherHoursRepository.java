package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import teachers.biniProject.Entity.TeacherHours;
import teachers.biniProject.HelperClasses.HoursByEmpIdAndReform;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TeacherHoursRepository extends JpaRepository<TeacherHours, Integer> {

    @Query(value = "select * from teacher_hours where mossad_id in :mossadId AND " +
            "employment_code = :empCode AND begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherHours> findByMossadIdAndEmpCode(@Param("mossadId") List<Integer> mossadId,
                                      @Param("empCode") int empCode,
                                      @Param("begda") Date begda,
                                      @Param("endda") Date endda);

    @Query(value = "select emp_id as empId, sum(hours) as hours, reform_type as reformType " +
            "from teacher_hours where mossad_id =:mossadId AND " +
            "begda >= :begda AND endda <= :endda " +
            "group by emp_id, reform_type",
            nativeQuery = true)
    List<Object[]> empHoursSumByMossadId(@Param("mossadId") int mossadId,
                                                      @Param("begda") Date begda,
                                                      @Param("endda") Date endda);


    @Query(value = "select * from teacher_hours where emp_id = :empId AND mossad_id =:mossadId AND  " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherHours> findByEmpIdAndMossadId(@Param("empId") String empId,
                                              @Param("mossadId") int mossadId,
                                              @Param("begda") Date begda,
                                              @Param("endda") Date endda);

    @Query(value = "select * from teacher_hours where emp_id = :empId AND mossad_id =:mossadId AND " +
            "employment_code =:empCode AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    List<TeacherHours> findByEmpIdAndMossadIdAndEmpCode(@Param("empId") String empId,
                                                        @Param("mossadId") int mossadId,
                                                        @Param("empCode") int empCode,
                                                        @Param("begda") Date begda,
                                                        @Param("endda") Date endda);


    @Query(value = "select distinct est.emp_id as empId," +
            "            ROUND(sum(est.hours) ,2) as estemateHours," +
            "            IFNULL(ROUND((select sum(hours.hours)" +
            "            from teacher_info as hours " +
            "            where hours.emp_id = est.emp_id " +
            "            AND hours.employment_code = est.employment_code " +
            "            AND begda >= :begda" +
            "            AND endda <= :endda" +
            "            AND hours.mossad_id = :mossadId),2),0) as actualHours," +
            "            e.first_name as firstName, " +
            "            e.last_name as lastName, " +
            "            est.employment_code as empCode" +
            "            from teacher_hours as est" +
            "            left join employees e on est.emp_id = e.emp_id" +
            "            where est.begda >= :begda" +
            "              AND est.endda <= :endda " +
            "              AND est.mossad_Id = :mossadId" +
            "            group by est.emp_id,e.first_name, e.last_name, actualHours,employment_code",
            nativeQuery = true)
    List<Object[]> findAllGaps(@Param("begda") Date begda,
                               @Param("endda") Date endda,
                               @Param("mossadId") int mossadId);

    @Transactional
    @Modifying
    @Query(value = "delete from teacher_hours where emp_id = :empId AND mossad_id = :mossadId AND " +
            "employment_code = :empCode AND " +
            "begda >= :begda AND endda <= :endda",
            nativeQuery = true)
    void deleteByEmpIdAndMossadIdAndEmpCode(@Param("empId") String empId,
                                            @Param("mossadId") int mossadId,
                                            @Param("empCode") int empCode,
                                            @Param("begda") Date begda,
                                            @Param("endda") Date endda);


    @Transactional
    @Modifying
    @Query(value = "delete from teacher_hours where emp_id = :empId AND " +
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
