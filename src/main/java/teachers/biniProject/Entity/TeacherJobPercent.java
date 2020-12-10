package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.EmpIdYearComositeKey;

import javax.persistence.*;

@Entity
@IdClass(EmpIdYearComositeKey.class)
@Table(name = "teacher_job_percent")
public class TeacherJobPercent {

    @Id
    @Column(name = "emp_id")
    private String empId;

    @Id
    @Column(name = "mossad_id")
    private String mossadId;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name = "job_percent")
    private float jobPercent;

    public TeacherJobPercent() {
        super();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMossadId() {
        return mossadId;
    }

    public void setMossadId(String mossadId) {
        this.mossadId = mossadId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getJobPercent() {
        return jobPercent;
    }

    public void setJobPercent(float jobPercent) {
        this.jobPercent = jobPercent;
    }
}
