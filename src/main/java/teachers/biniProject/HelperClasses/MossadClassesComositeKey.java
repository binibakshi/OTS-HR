package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class MossadClassesComositeKey implements Serializable {
    private int mossadId;
    private int year;
    private int grade;
    private int classNumber;

    public MossadClassesComositeKey() {
        super();
    }

    public MossadClassesComositeKey(int mossadId, int year, int grade, int classNumber) {
        this.mossadId = mossadId;
        this.year = year;
        this.grade = grade;
        this.classNumber = classNumber;
    }
}
