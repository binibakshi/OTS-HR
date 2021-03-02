package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;

import javax.persistence.*;

@Entity
@IdClass(teachersRewardsCompositeKey.class)
@Table(name = "TEACHERS_REWARDS")
public class TeachersRewards {

    @Id
    @Column(name = "emp_id")
    private int empId;

    @Id
    @Column(name = "reward_id")
    private int rewardId;

    @Id
    @Column(name = "reward_type") //1 = Bagrut, 2 = Job
    private int rewardType;

    @Id
    @Column(name = "mossad_id")
    private int mossadId;

    @Id
    @Column(name = "year")
    private int year;

    @Id
    @Column(name = "class", nullable = true)
    private int teachingClass;

    @Column(name = "reform_id")
    private int reformId;

    @Column(name = "is_external")
    private boolean isExternal;

    @Column(name = "is_split")
    private boolean isSplit;

    @Column(name = "students")
    private int students;

    @Column(name = "hours", nullable = false)
    private float hours;

    @Column(name = "percent", nullable = false)
    private float percent;

    public TeachersRewards() {
        super();
    }

    public TeachersRewards(int empId, int rewardId, int rewardType, int mossadId, int year, int teachingClass, int reformId, boolean isExternal, boolean isSplit, int students, float hours, float percent) {
        this.empId = empId;
        this.rewardId = rewardId;
        this.rewardType = rewardType;
        this.mossadId = mossadId;
        this.year = year;
        this.teachingClass = teachingClass;
        this.reformId = reformId;
        this.isExternal = isExternal;
        this.isSplit = isSplit;
        this.students = students;
        this.hours = hours;
        this.percent = percent;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
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

    public int getTeachingClass() {
        return teachingClass;
    }

    public void setTeachingClass(int teachingClass) {
        this.teachingClass = teachingClass;
    }

    public int getReformId() {
        return reformId;
    }

    public void setReformId(int reformId) {
        this.reformId = reformId;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public void setSplit(boolean split) {
        isSplit = split;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
