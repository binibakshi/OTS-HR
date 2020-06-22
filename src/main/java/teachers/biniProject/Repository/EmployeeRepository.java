package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import teachers.biniProject.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
