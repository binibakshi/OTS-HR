package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Exeption.DataNotFoundExeption;
import teachers.biniProject.Repository.EmpToMossadRepository;
import teachers.biniProject.Repository.EmployeeRepository;
import teachers.biniProject.Repository.WeeklyHoursRepository;
import teachers.biniProject.Repository.reformTypeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository emplyeeReposiroty;

    @Autowired
    private WeeklyHoursService weeklyHoursService;

    @Autowired
    private WeeklyHoursRepository weeklyHoursRepository;

    @Autowired
    private reformTypeRepository reformTypeRepository;

    @Autowired
    private EmpToMossadRepository empToMossadRepository;

    public Employee save(Employee employee) {
        Calendar testDate = Calendar.getInstance();
        employee.setBegda(testDate.getTime());
        testDate.set(9999, Calendar.DECEMBER, 31);
        employee.setEndda(testDate.getTime());
        return emplyeeReposiroty.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return emplyeeReposiroty.saveAll(employees);
    }

    public List<Employee> findAll() {
        return emplyeeReposiroty.findAll();
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
//		return emplyeeReposiroty.findById(id).orElse(null);
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

    public float getMaxJobPercent(String tz) {
        return reformTypeRepository.findById(
                emplyeeReposiroty.findById(tz).get().getReform_type()).
                get().getmaxJobPercent();

    }

    public List<Employee> getByFirstName(String firstName) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getFirstName().equals(firstName)).
                collect(Collectors.toList());
    }

    public List<Employee> getBylastName(String lastName) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getFirstName().equals(lastName)).
                collect(Collectors.toList());
    }

    public List<Employee> getByAnyName(String name) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getFirstName().equals(name) ||
                        el.getLastName().equals(name)).
                collect(Collectors.toList());
    }

    public List<Employee> getByFullName(String firstName, String lastName) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getFirstName().equals(firstName) &&
                        el.getLastName().equals(lastName)).
                collect(Collectors.toList());
    }

    public List<Employee> getByIsmother(boolean isMother) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.isMother() == isMother).
                collect(Collectors.toList());
    }

    public List<Employee> getByGender(char gender) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getGender() == gender).
                collect(Collectors.toList());
    }

    public List<Employee> getByStratDates(Date startDate, Date endDate) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> startDate.before(el.getBegda()) &&
                        endDate.after(el.getBegda())).
                collect(Collectors.toList());
    }

    public List<Employee> getByEndDates(Date startDate, Date endDate) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> startDate.before(el.getEndda()) &&
                        endDate.after(el.getEndda())).
                collect(Collectors.toList());
    }

    @SuppressWarnings("deprecation")
    public List<Employee> getByYear(int currYear) {
        return this.emplyeeReposiroty.findAll().stream().
                filter(el -> el.getBegda().getYear() <= currYear &&
                        el.getEndda().getYear() >= currYear).
                collect(Collectors.toList());
    }

    public List<Employee> getMossad(int mossadId) {
        List<String> empInMossad = this.empToMossadRepository.findAll().stream().
                filter(el -> el.getMossadId() == mossadId).
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
