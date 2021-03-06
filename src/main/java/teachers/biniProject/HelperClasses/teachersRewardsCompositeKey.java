package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class teachersRewardsCompositeKey implements Serializable {

    private String empId;
    private int rewardId;
    private int rewardType;
    private int mossadId;
    private int year;
    private int teachingClass;
    private int grade;

    public teachersRewardsCompositeKey() {
        super();
    }

    public teachersRewardsCompositeKey(String empId, int rewardId, int rewardType, int mossadId, int year, int teachingClass, int grade) {
        this.empId = empId;
        this.rewardId = rewardId;
        this.rewardType = rewardType;
        this.mossadId = mossadId;
        this.year = year;
        this.teachingClass = teachingClass;
        this.grade = grade;
    }
}
