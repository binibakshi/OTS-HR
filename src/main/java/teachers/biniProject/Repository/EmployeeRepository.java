package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "select * "
            + "from employees e inner join mossadot as m on ( :mossadId is null or  e.emp_id = m.emp_id)"
            + "where (:empId IS NULL OR e.emp_id = :empId) AND"
            + "	((:firstName IS NULL OR e.first_name = :firstName) or"
            + "	(:lastName IS NULL OR e.last_name = :lastName)) AND"
            + "	(:mossadId IS NULL OR m.mosad_id = :mossadId) AND"
//			+ "	(:begda IS NULL OR e.begda >= :begda) AND"
//			+ "	(:endda IS NULL OR e.begda <= :endda) AND"
            + "	(:gender = '' OR :gender is null or e.gender = :gender)" +
            "group by e.emp_id",
            nativeQuery = true)
    List<Employee> findByTest(@Param("empId") String empId,
                              @Param("mossadId") int mossadId,
                              @Param("firstName") String firstName,
                              @Param("lastName") String lastName,
//                              @Param("begda") Date begda,
//                              @Param("endda") Date endda,
                              @Param("gender") char gender
    );

}
