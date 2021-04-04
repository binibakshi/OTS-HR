package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;

import javax.persistence.*;

@Entity
@IdClass(teachersRewardsCompositeKey.class)
@Table(name = "TEACHERS_REWARDS")
public class TeachersRewards {

    @Id
    @Column(name = "emp_id")
    private String empId;

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
    @Column(name = "class", nullable = true) // relevant only for bagrut rewards
    private int teachingClass;

    @Id
    @Column(name = "grade", nullable = true) // relevant only for bagrut rewards
    private int grade;

    @Column(name = "employment_code")
    private int employmentCode;

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

    @Column(name = "hours_ots")
    private float hoursOTS;

    @Column(name = "hours_normal")
    private float hoursNormal;

    @Column(name = "percent_ots")
    private float percentOTS;

    @Column(name = "percent_normal")
    private float percentNormal;

    @Column(name = "actual_units")
    private int actualUnits;

    @Column(name = "second_teacher")
    private String secondTeacher;

    public TeachersRewards() {
        super();
    }

    public TeachersRewards(String empId, int rewardId, int rewardType, int mossadId, int year, int teachingClass, int grade, int employmentCode, int reformId, boolean isExternal, boolean isSplit, int students, float hours, float percent, float hoursOTS, float hoursNormal, float percentOTS, float percentNormal, int actualUnits, String secondTeacher) {
        this.empId = empId;
        this.rewardId = rewardId;
        this.rewardType = rewardType;
        this.mossadId = mossadId;
        this.year = year;
        this.teachingClass = teachingClass;
        this.grade = grade;
        this.employmentCode = employmentCode;
        this.reformId = reformId;
        this.isExternal = isExternal;
        this.isSplit = isSplit;
        this.students = students;
        this.hours = hours;
        this.percent = percent;
        this.hoursOTS = hoursOTS;
        this.hoursNormal = hoursNormal;
        this.percentOTS = percentOTS;
        this.percentNormal = percentNormal;
        this.actualUnits = actualUnits;
        this.secondTeacher = secondTeacher;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getEmploymentCode() {
        return employmentCode;
    }

    public void setEmploymentCode(int employmentCode) {
        this.employmentCode = employmentCode;
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

    public float getHoursOTS() {
        return hoursOTS;
    }

    public void setHoursOTS(float hoursOTS) {
        this.hoursOTS = hoursOTS;
    }

    public float getHoursNormal() {
        return hoursNormal;
    }

    public void setHoursNormal(float hoursNormal) {
        this.hoursNormal = hoursNormal;
    }

    public float getPercentOTS() {
        return percentOTS;
    }

    public void setPercentOTS(float percentOTS) {
        this.percentOTS = percentOTS;
    }

    public float getPercentNormal() {
        return percentNormal;
    }

    public void setPercentNormal(float percentNormal) {
        this.percentNormal = percentNormal;
    }

    public int getActualUnits() {
        return actualUnits;
    }

    public void setActualUnits(int actualUnits) {
        this.actualUnits = actualUnits;
    }

    public String getSecondTeacher() {
        return secondTeacher;
    }

    public void setSecondTeacher(String secondTeacher) {
        this.secondTeacher = secondTeacher;
    }
}
