package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import teachers.biniProject.Entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "select distinct mossad_id " +
            "from employees as e " +
            "inner join teacher_info as t on e.emp_id = t.emp_id " +
            "where t.emp_id = :empId",
            nativeQuery = true)
    List<Integer> getMossadByEmpId(@Param("empId") String empId);

}
