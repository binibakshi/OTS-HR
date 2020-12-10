package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.MossadHoursComositeKey;

import javax.persistence.*;

@Entity
@IdClass(MossadHoursComositeKey.class)
@Table(name = "Mossad_hours")
public class MossadHours {

    @Id
    @Column(name = "MOSSAD_ID")
    private int mossadId;

    @Id
    @Column(name = "YEAR")
    private int year;

    @Column(name = "MAX_HOURS")
    private int maxHours;

    @Column(name = "CURR_HOURS")
    private float currHours;

    public MossadHours() {
        super();
    }

    public MossadHours(int mossadId, int year, int maxHours, int currHours) {
        this.mossadId = mossadId;
        this.year = year;
        this.maxHours = maxHours;
        this.currHours = currHours;
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

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }

    public float getCurrHours() {
        return currHours;
    }

    public void setCurrHours(float currHours) {
        this.currHours = currHours;
    }
}
