package teachers.biniProject.Entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher_hours")
public class TeacherHours {

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

    @Column(name = "employment_code")
    private int empCode;

    @Column(name = "REFORM_TYPE")
    private int reformType;

    @Column(name = "HOURS")
    private float hours;

    @Column(name = "CHANGED_BY", length = 55)
    private String changedBy;

    public TeacherHours() {
        super();
    }

    public TeacherHours(int id, String empId, Date begda, Date endda, int mossadId, int empCode, int reformType, float hours, String changedBy) {
        Id = id;
        this.empId = empId;
        this.begda = begda;
        this.endda = endda;
        this.mossadId = mossadId;
        this.empCode = empCode;
        this.reformType = reformType;
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

    public void setMossadId(int mossadId) {
        this.mossadId = mossadId;
    }

    public int getEmpCode() {
        return empCode;
    }

    public void setEmpCode(int empCode) {
        this.empCode = empCode;
    }

    public int getReformType() {
        return reformType;
    }

    public void setReformType(int reformType) {
        this.reformType = reformType;
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
