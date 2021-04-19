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
    private int mossadId;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name = "job_percent" ,nullable = false)
    private float jobPercent;

    @Column(name = "estimate_job_percent",nullable = false)
    private float estimateJobPercent;

    public TeacherJobPercent() {
        super();
    }

    public TeacherJobPercent(String empId, int mossadId, int year, float jobPercent, float estimateJobPercent) {
        this.empId = empId;
        this.mossadId = mossadId;
        this.year = year;
        this.jobPercent = jobPercent;
        this.estimateJobPercent = estimateJobPercent;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getMossadId() {
        return mossadId;
    }

    public void setMossadId(int mossadId) {
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

    public float getEstimateJobPercent() {
        return estimateJobPercent;
    }

    public void setEstimateJobPercent(float estimateJobPercent) {
        this.estimateJobPercent = estimateJobPercent;
    }
}
