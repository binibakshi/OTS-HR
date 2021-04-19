package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Employee;
import teachers.biniProject.Entity.CalcHours;
import teachers.biniProject.Repository.CalcHoursRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalcHoursService {

    @Autowired
    private CalcHoursRepository CalcHoursRepository;

    @Autowired
    private EmployeeService employeeService;

    public List<CalcHours> findAll() {
        return CalcHoursRepository.findAll();
    }

    public List<CalcHours> getEmployeeByReform(int reformType, boolean isMother, int ageHours) {

        return CalcHoursRepository.option(isMother, ageHours, reformType);

    }

    public CalcHours getByJobPercent(int reformType, boolean isMother, int ageHours, float jobPercent) {
        List<CalcHours> calcHours = CalcHoursRepository.findAll().stream()
                .filter(record -> record.getReformType() == reformType &&
                        record.getAgeHours() == ageHours &&
                        record.isMother() == isMother &&
                        record.getJobPercent() == jobPercent).collect(Collectors.toList());
        if (calcHours.isEmpty()) {
            return null;
        } else {
            return calcHours.get(0);
        }
    }

    public CalcHours getByFrontalHours(int reformType, boolean isMother, int ageHours, float frontalHours) {

        List<CalcHours> calcHours = CalcHoursRepository.findAll().stream()
                .filter(record -> record.getReformType() == reformType &&
                        record.getAgeHours() == ageHours &&
                        record.isMother() == isMother &&
                        record.getFrontalHours() == frontalHours).collect(Collectors.toList());
        if (calcHours.isEmpty()) {
            return null;
        } else {
            return calcHours.get(0);
        }
    }

    CalcHours getByFrontalTzReformType(String tz, float frontalHours, int reformType) {
        Employee emp = employeeService.findById(tz);
        return this.getByFrontalHours(reformType, emp.isMother(), employeeService.getAgeHours(emp.getBirthDate()), frontalHours);
    }

    public float getJobPercent(int reformType, String empId, float allHours) {
        int ageHours = 0;
        Employee emp = employeeService.findById(empId);
        ageHours = employeeService.getAgeHours(emp.getBirthDate());
        emp = employeeService.findById(empId);

        float jobPercent = 0;
        switch (reformType) {
            case 1: // Olam yashan upper class
                jobPercent = this.getOlamYashanJobPercent(ageHours, emp.isMother(), allHours);
                break;
            case 7: // Olam yashan middle class
                jobPercent = this.getOlamYashanJobPercent(ageHours, emp.isMother(), allHours);
                break;
            case 2: // Ofek hadash
                jobPercent = this.getOfekHasashJobPercent(ageHours, emp.isMother(), allHours);
                break;
            case 5: // OZ letmura
                jobPercent = this.getOzLetmuraJobPercent(ageHours, emp.isMother(), allHours);
                break;
            case 8: // administration
                jobPercent = this.getMinhalaJobPercent(ageHours, emp.isMother(), allHours);
                break;
            default:
                break;
        }
        return jobPercent;
    }

    private float getOlamYashanJobPercent(int ageHours, boolean isMother, float frontalHours) {
        float jobPercent = 0, fullJobHours = 24;
        fullJobHours -= ageHours;
        jobPercent = (frontalHours / fullJobHours) * 100;
        if (isMother && jobPercent > 79) {
            jobPercent = jobPercent + 10;
        }
        return jobPercent;
    }

    private float getOfekHasashJobPercent(int ageHours, boolean isMother, float allHours) {
        float jobPercent = 0, fullJobHours = 36;
        fullJobHours -= ageHours;
        if (isMother) {
            fullJobHours -= 2;
        }
        jobPercent = (allHours / fullJobHours) * 100;
        return jobPercent;
    }

    private float getOzLetmuraJobPercent(int ageHours, boolean isMother, float allHours) {
        float jobPercent = 0, fullJobHours = 40;
        fullJobHours -= ageHours;
        jobPercent = (allHours / fullJobHours) * 100;
        if (isMother && jobPercent > 78.75) {
            jobPercent = jobPercent + 7;
        }
        return jobPercent;
    }

    private float getMinhalaJobPercent(int ageHours, boolean isMother, float allHours) {
        float jobPercent = 0, fullJobHours = 20;
        jobPercent = (allHours / fullJobHours) * 100;
        return jobPercent;
    }


}
