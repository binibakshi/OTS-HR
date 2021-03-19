package teachers.biniProject.Entity;

import javax.persistence.*;

@Entity
@Table(name = "JOB_REWARDS")
public class JobRewards {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_key")
    private int recordkey;

    @Column(name = "employment_code")
    private int employmentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "min_hours")
    private float minHours;

    @Column(name = "max_hours")
    private float maxHours;

    @Column(name = "min_percent")
    private float minPercent;

    @Column(name = "max_percent")
    private float maxPercent;

    @Column(name = "percent1")
    private float percent1;

    @Column(name = "percent2")
    private float percent2;

    @Column(name = "percent3")
    private float percent3;

    @Column(name = "percent4")
    private float percent4;

    @Column(name = "percent5")
    private float percent5;

    @Column(name = "desc_link")
    private String descLink;

    public JobRewards() {
        super();
    }

    public JobRewards(int recordkey, int employmentCode, String description, float minHours, float maxHours, float minPercent, float maxPercent, float percent1, float percent2, float percent3, float percent4, float percent5, String descLink) {
        this.recordkey = recordkey;
        this.employmentCode = employmentCode;
        this.description = description;
        this.minHours = minHours;
        this.maxHours = maxHours;
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
        this.percent1 = percent1;
        this.percent2 = percent2;
        this.percent3 = percent3;
        this.percent4 = percent4;
        this.percent5 = percent5;
        this.descLink = descLink;
    }

    public int getRecordkey() {
        return recordkey;
    }

    public void setRecordkey(int recordkey) {
        this.recordkey = recordkey;
    }

    public int getEmploymentCode() {
        return employmentCode;
    }

    public void setEmploymentCode(int employmentCode) {
        this.employmentCode = employmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMinHours() {
        return minHours;
    }

    public void setMinHours(float minHours) {
        this.minHours = minHours;
    }

    public float getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(float maxHours) {
        this.maxHours = maxHours;
    }

    public float getMinPercent() {
        return minPercent;
    }

    public void setMinPercent(float minPercent) {
        this.minPercent = minPercent;
    }

    public float getMaxPercent() {
        return maxPercent;
    }

    public void setMaxPercent(float maxPercent) {
        this.maxPercent = maxPercent;
    }

    public float getPercent1() {
        return percent1;
    }

    public void setPercent1(float percent1) {
        this.percent1 = percent1;
    }

    public float getPercent2() {
        return percent2;
    }

    public void setPercent2(float percent2) {
        this.percent2 = percent2;
    }

    public float getPercent3() {
        return percent3;
    }

    public void setPercent3(float percent3) {
        this.percent3 = percent3;
    }

    public float getPercent4() {
        return percent4;
    }

    public void setPercent4(float percent4) {
        this.percent4 = percent4;
    }

    public float getPercent5() {
        return percent5;
    }

    public void setPercent5(float percent5) {
        this.percent5 = percent5;
    }

    public String getDescLink() {
        return descLink;
    }

    public void setDescLink(String descLink) {
        this.descLink = descLink;
    }
}
