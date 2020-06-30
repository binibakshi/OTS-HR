package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "select * "
            + "from employees e "
            + "where (:empId IS NULL OR e.emp_id = :empId) AND"
            + "	(:firstName IS NULL OR e.first_name = :firstName) AND"
            + "	(:lastName IS NULL OR e.last_name = :lastName) AND"
//			+ "	(:begda IS NULL OR e.begda >= :begda) AND"
//			+ "	(:endda IS NULL OR e.begda <= :endda) AND"
            + "	(:gender = '' OR e.gender = :gender)",
            nativeQuery = true)
    List<Employee> findByTest(@Param("empId") String empId,
                              @Param("firstName") String firstName,
                              @Param("lastName") String lastName,
//                              @Param("begda") Date begda,
//                              @Param("endda") Date endda,
                              @Param("gender") char gender
    );

}
