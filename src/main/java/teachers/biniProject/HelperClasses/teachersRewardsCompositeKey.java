package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class teachersRewardsCompositeKey implements Serializable {

    private int empId;
    private int rewardId;
    private int mossadId;
    private int year;
    private int teachingClass;

    public teachersRewardsCompositeKey() {
        super();
    }

    public teachersRewardsCompositeKey(int empId, int rewardId,
                                       int mossadId, int year, int teachingClass) {
        this.empId = empId;
        this.rewardId = rewardId;
        this.mossadId = mossadId;
        this.year = year;
        this.teachingClass = teachingClass;
    }
}
