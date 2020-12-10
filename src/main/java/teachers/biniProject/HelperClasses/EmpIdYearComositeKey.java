package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class EmpIdYearComositeKey implements Serializable {
    private String empId;
    private int mossadId;
    private int year;

    public EmpIdYearComositeKey() {
        super();
    }

    public EmpIdYearComositeKey(String empId, int mossadId, int year) {
        this.empId = empId;
        this.mossadId = mossadId;
        this.year = year;
    }
}
