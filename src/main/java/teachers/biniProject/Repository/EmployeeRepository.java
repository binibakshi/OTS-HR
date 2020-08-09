package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
