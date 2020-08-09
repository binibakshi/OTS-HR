package teachers.biniProject.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEACHER_INFO")
public class TeacherEmploymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_key")
    private int Id;

    @Column(name = "EMP_ID")
    private String empId;

    @Column(name = "BEGDA")
    private Date begda;

    @Column(name = "ENDDA")
    private Date endda;

    @Column(name = "MOSSAD_ID")
    private int mossadId;

    @Column(name = "EMPLOYMENT_CODE")
    private int empCode;

    @Column(name = "REFORM_TYPE")
    private int reformType;

    @Column(name = "DAY")
    private int day;

    @Column(name = "STATUS")
    private char status;

    @Column(name = "HOURS")
    private float hours;

    @Column(name = "CHANGED_BY", length = 55)
    private String changedBy;

    public TeacherEmploymentDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TeacherEmploymentDetails(TeacherEmploymentDetails teacherEmploymentDetails) {
        if (teacherEmploymentDetails != null) {
            Id = teacherEmploymentDetails.getId();
            this.empId = teacherEmploymentDetails.getEmpId();
            this.begda = teacherEmploymentDetails.getBegda();
            this.endda = teacherEmploymentDetails.getEndda();
            this.mossadId = teacherEmploymentDetails.getMossadId();
            this.empCode = teacherEmploymentDetails.getEmpCode();
            this.reformType = teacherEmploymentDetails.getReformType();
            this.day = teacherEmploymentDetails.getDay();
            this.hours = teacherEmploymentDetails.getHours();
            this.changedBy = teacherEmploymentDetails.getChangedBy();
        }
    }

    public TeacherEmploymentDetails(int id, String empId, Date begda, Date endda, int mossadId, int empCode, int day,
                                    char status, float hours, String changedBy) {
        super();
        Id = id;
        this.empId = empId;
        this.begda = begda;
        this.endda = endda;
        this.mossadId = mossadId;
        this.empCode = empCode;
        this.day = day;
        this.status = status;
        this.hours = hours;
        this.changedBy = changedBy;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getBegda() {
        return begda;
    }

    public void setBegda(Date begda) {
        this.begda = begda;
    }

    public Date getEndda() {
        return endda;
    }

    public void setEndda(Date endda) {
        this.endda = endda;
    }

    public int getMossadId() {
        return mossadId;
    }

    public void setMossadId(int mosadId) {
        this.mossadId = mosadId;
    }

    public int getEmpCode() {
        return empCode;
    }

    public void setEmpCode(int empCode) {
        this.empCode = empCode;
    }

    public int getReformType() {
        return this.reformType;
    }

    public void setReformType(int reformType) {
        this.reformType = reformType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }


}
