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

    @Column(name = "hours1")
    private float hours1;

    @Column(name = "hours2")
    private float hours2;

    @Column(name = "hours3")
    private float hours3;

    @Column(name = "hours4")
    private float hours4;

    @Column(name = "hours5")
    private float hours5;

    @Column(name = "min_percent")
    private float minPercent;

    @Column(name = "max_percent")
    private float maxPercent;

    @Column(name = "desc_link")
    private String descLink;

    public JobRewards() {
        super();
    }

    public JobRewards(int recordkey, int employmentCode, String description, float minHours, float maxHours, float hours1, float hours2, float hours3, float hours4, float hours5, float minPercent, float maxPercent, String descLink) {
        this.recordkey = recordkey;
        this.employmentCode = employmentCode;
        this.description = description;
        this.minHours = minHours;
        this.maxHours = maxHours;
        this.hours1 = hours1;
        this.hours2 = hours2;
        this.hours3 = hours3;
        this.hours4 = hours4;
        this.hours5 = hours5;
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
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

    public float getHours1() {
        return hours1;
    }

    public void setHours1(float hours1) {
        this.hours1 = hours1;
    }

    public float getHours2() {
        return hours2;
    }

    public void setHours2(float hours2) {
        this.hours2 = hours2;
    }

    public float getHours3() {
        return hours3;
    }

    public void setHours3(float hours3) {
        this.hours3 = hours3;
    }

    public float getHours4() {
        return hours4;
    }

    public void setHours4(float hours4) {
        this.hours4 = hours4;
    }

    public float getHours5() {
        return hours5;
    }

    public void setHours5(float hours5) {
        this.hours5 = hours5;
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

    public String getDescLink() {
        return descLink;
    }

    public void setDescLink(String descLink) {
        this.descLink = descLink;
    }
}
