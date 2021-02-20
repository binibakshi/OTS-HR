package teachers.biniProject.Entity;

import javax.persistence.*;

@Entity
@Table(name = "BAGRUT_REWARDS")
public class BagrutRewards {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_key")
    private int recordkey;

    @Column(name = "study_id")
    private int studyId;

    @Column(name = "study_name")
    private String studyName;

    @Column(name = "study_units")
    private int studyUnits;

    @Column(name = "questionnaire")
    private String questionnaire;

    @Column(name = "internal_hours_reward")
    private float internalHoursReward;

    @Column(name = "internal_percent_reward")
    private float internalPercentReward;

    @Column(name = "external_hours_reward")
    private float externalHoursReward;

    @Column(name = "external_percent_reward")
    private float externalPercentReward;

    public BagrutRewards() {
        super();
    }

    public BagrutRewards(int recordkey, int studyId, String studyName, int studyUnits, String questionnaire,
                         float internalHoursReward, float internalPercentReward, float externalHoursReward,
                         float externalPercentReward) {
        this.recordkey = recordkey;
        this.studyId = studyId;
        this.studyName = studyName;
        this.studyUnits = studyUnits;
        this.questionnaire = questionnaire;
        this.internalHoursReward = internalHoursReward;
        this.internalPercentReward = internalPercentReward;
        this.externalHoursReward = externalHoursReward;
        this.externalPercentReward = externalPercentReward;
    }

    public int getRecordkey() {
        return recordkey;
    }

    public void setRecordkey(int recordkey) {
        this.recordkey = recordkey;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public int getStudyUnits() {
        return studyUnits;
    }

    public void setStudyUnits(int studyUnits) {
        this.studyUnits = studyUnits;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }

    public float getInternalHoursReward() {
        return internalHoursReward;
    }

    public void setInternalHoursReward(float internalHoursReward) {
        this.internalHoursReward = internalHoursReward;
    }

    public float getInternalPercentReward() {
        return internalPercentReward;
    }

    public void setInternalPercentReward(float internalPercentReward) {
        this.internalPercentReward = internalPercentReward;
    }

    public float getExternalHoursReward() {
        return externalHoursReward;
    }

    public void setExternalHoursReward(float externalHoursReward) {
        this.externalHoursReward = externalHoursReward;
    }

    public float getExternalPercentReward() {
        return externalPercentReward;
    }

    public void setExternalPercentReward(float externalPercentReward) {
        this.externalPercentReward = externalPercentReward;
    }
}
