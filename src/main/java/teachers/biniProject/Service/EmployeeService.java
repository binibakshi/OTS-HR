package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Exeption.DataNotFoundExeption;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Repository.EmployeeRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository emplyeeReposiroty;

    @Autowired
    private EmpToMossadService empToMossadService;

    @Autowired
    private TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

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
        if (this.emplyeeReposiroty.existsById(empId)) {
            this.emplyeeReposiroty.deleteById(empId);
        } else {
            throw new GenericException("לא נמצא עובד עם תעודת זהות" + empId);
        }
    }

    public int getAgeHours(Date birthdate) {
        @SuppressWarnings("deprecation")
        Date currDate = new Date();
        int yearsDiff = currDate.getYear() - birthdate.getYear();

        if (currDate.getMonth() >= 8) {
            yearsDiff += 1;
        }
        if (yearsDiff >= 55) {
            return 4;
        } else if (yearsDiff < 50) {
            return 0;
        } else {
            return 2;
        }
    }

    public int getAgeHours(Date birthdate, int year) {
        @SuppressWarnings("deprecation")
        Date currDate = new Date(year - 1900, Calendar.SEPTEMBER, 1);
        int yearsDiff = currDate.getYear() - birthdate.getYear();

        if (currDate.getMonth() >= 8) {
            yearsDiff += 1;
        }
        if (yearsDiff >= 55) {
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

    public List<Employee> getAllEmpInMossad(int mossadId, Date begda, Date endda) {
        return this.emplyeeReposiroty.findAllById(this.teacherEmploymentDetailsRepository.findAllEmpByMossadId(mossadId, begda, endda));
    }

    private void onApprovedEmployee(Employee employee) {
    }

    private void onDenyEmployee(String tz) {
    }
}
