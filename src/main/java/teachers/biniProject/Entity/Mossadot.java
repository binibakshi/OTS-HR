package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOSSADOT")
public class Mossadot {

    @Id
    @Column(name = "MOSSAD_ID")
    private int mossadId;

    @Column(name = "MOSSAD_NAME")
    private String mossadName;

    @Column(name = "MOSSAD_TYPE")
    private char mossadType;

    @Column(name = "MAX_HOURS")
    private int maxHours;

    @Column(name = "CURR_HOURS")
    private int currHours;

    public Mossadot() {
        super();
    }

    public Mossadot(int mossadId, String mossadName, char mossadType, int maxHours, int currHours) {
        this.mossadId = mossadId;
        this.mossadName = mossadName;
        this.mossadType = mossadType;
        this.maxHours = maxHours;
        this.currHours = currHours;
    }

    public Mossadot(Mossadot mossad){
        this.mossadId = mossad.mossadId;
        this.mossadName = mossad.getMossadName();
        this.mossadType = mossad.getMossadType();
        this.maxHours = mossad.getMaxHours();
        this.currHours = mossad.getCurrHours();
    }

    public int getMossadId() {
        return mossadId;
    }

    public void setMossadId(int mossadId) {
        this.mossadId = mossadId;
    }

    public String getMossadName() {
        return mossadName;
    }

    public void setMossadName(String mossadName) {
        this.mossadName = mossadName;
    }

    public char getMossadType() {
        return mossadType;
    }

    public void setMossadType(char mossadType) {
        this.mossadType = mossadType;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }

    public int getCurrHours() {
        return currHours;
    }

    public void setCurrHours(int currHours) {
        this.currHours = currHours;
    }
}
