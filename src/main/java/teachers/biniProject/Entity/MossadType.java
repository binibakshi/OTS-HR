package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class MossadType {

    @Id
    @Column(name = "TYPE_ID")
    private char typeId;

    @Column(name = "TYPE_NAME")
    private String typeName;

    public MossadType() {
        super();
    }

    public MossadType(char typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public char getTypeId() {
        return typeId;
    }

    public void setTypeId(char typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

