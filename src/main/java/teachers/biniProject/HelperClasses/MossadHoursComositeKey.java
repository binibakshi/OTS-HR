package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class MossadHoursComositeKey implements Serializable {
    private int mossadId;
    private int year;

    public MossadHoursComositeKey(){super();}

    public MossadHoursComositeKey(int mossadId, int year) {
        this.mossadId = mossadId;
        this.year = year;
    }
}
