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

    public Mossadot() {
        super();
    }

    public Mossadot(int mossadId, String mossadName, char mossadType) {
        this.mossadId = mossadId;
        this.mossadName = mossadName;
        this.mossadType = mossadType;
    }

    public Mossadot(Mossadot mossad) {
        this.mossadId = mossad.mossadId;
        this.mossadName = mossad.getMossadName();
        this.mossadType = mossad.getMossadType();
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
}
