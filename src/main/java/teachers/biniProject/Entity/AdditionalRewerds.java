package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDITIONAL_REWARDS")
public class AdditionalRewerds {

    @Id
    @Column(name = "record_key")
    private int recordkey;

    @Column(name = "profession")
    private String profession;

    @Column(name = "study_units")
    private int studyUnits;

    @Column(name = "questionnaire")
    private String questionnaire;

    @Column(name = "is_external")
    private boolean isExternal;

    @Column(name = "hours_reward")
    private float hoursReward;

    public AdditionalRewerds(int recordkey, String profession, int studyUnits, String questionnaire, boolean isExternal, float hoursReward) {
        this.recordkey = recordkey;
        this.profession = profession;
        this.studyUnits = studyUnits;
        this.questionnaire = questionnaire;
        this.isExternal = isExternal;
        this.hoursReward = hoursReward;
    }

    public AdditionalRewerds() {
        super();
    }

    public int getRecordkey() {
        return recordkey;
    }

    public void setRecordkey(int recordkey) {
        this.recordkey = recordkey;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public float getHoursReward() {
        return hoursReward;
    }

    public void setHoursReward(float hoursReward) {
        this.hoursReward = hoursReward;
    }
}
