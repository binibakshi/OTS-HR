package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;

import javax.persistence.*;

@Entity
@IdClass(EmpIdYearComositeKey.class)
@Table(name = "teacher_job_percent")
public class teacherJobPercent {

    @Id
    @Column(name = "emp_id")
    private String empId;

    @Id
    @Column(name = "year")
    private int Year;

    @Column(name = "job_percent")
    private float jobPercent;

    public teacherJobPercent() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public float getJobPercent() {
        return jobPercent;
    }

    public void setJobPercent(float jobPercent) {
        this.jobPercent = jobPercent;
    }
}
