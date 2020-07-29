package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Exeption.DataNotFoundExeption;
import teachers.biniProject.Repository.EmployeeRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository emplyeeReposiroty;

    @Autowired
    private EmpToMossadService empToMossadService;

    @Autowired
    private TeacherEmploymentDetailsService teacherEmploymentDetailsService;

    public Employee save(Employee employee) {
        Calendar testDate = Calendar.getInstance();
        employee.setBegda(testDate.getTime());
        testDate.set(9999, Calendar.DECEMBER, 31);
        employee.setEndda(testDate.getTime());
        employee.setStatus('A'); //Approved
        return emplyeeReposiroty.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return emplyeeReposiroty.saveAll(employees);
    }

    public List<Employee> findAll() {
        return emplyeeReposiroty.findAll();
    }

    public void deleteByEmpId(String empId) {
        if (this.emplyeeReposiroty.existsById(empId)){
            this.emplyeeReposiroty.deleteById(empId);
        }
        this.emplyeeReposiroty.deleteById(empId);
        this.empToMossadService.deleteByEmpId(empId);
        this.teacherEmploymentDetailsService.deleteByEmpId(empId);
    }

    public int getAgeHours(Date birthdate) {
        @SuppressWarnings("deprecation")
        long yearsDiff = ChronoUnit.YEARS.between(
                LocalDate.of(birthdate.getYear(), 12, 31)
                , LocalDate.now());
        if (yearsDiff > 55) {
            return 4;
        } else if (yearsDiff < 50) {
            return 0;
        } else {
            return 2;
        }
    }

    public Employee findById(String id) {
        return emplyeeReposiroty.findById(id).orElseThrow(() -> new DataNotFoundExeption(id));
    }

    public Employee updateStatus(Employee employee) {
        char status = employee.getStatus();

        // check if there is change at all
        if (emplyeeReposiroty.findById(employee.getEmpId()).equals(status)) {
            return null;
        }

        if (status == 'A') {
            this.onApprovedEmployee(employee);
            return emplyeeReposiroty.save(employee);
        } else if (status == 'D') {
            this.onDenyEmployee(employee.getEmpId());
            return emplyeeReposiroty.save(employee);
        } else {
            return null;
        }
    }

    public void delete(String empId) {
        this.emplyeeReposiroty.deleteById(empId);
    }

    public List<Employee> getMossad(int mossadId) {
        List<String> empInMossad = this.empToMossadService.findAllByMossad(mossadId).stream().
                map(el -> el.getEmpId()).
                collect(Collectors.toList());

        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> empInMossad.contains(el.getEmpId())).
                collect(Collectors.toList());
    }

    private void onApprovedEmployee(Employee employee) {
    }
    private void onDenyEmployee(String tz) {
    }
}
