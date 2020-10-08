package teachers.biniProject.HelperClasses;

import java.io.Serializable;

public class EmpIdYearComositeKey implements Serializable {
    private String empId;
    private int year;

    public EmpIdYearComositeKey() {
        super();
    }

    public EmpIdYearComositeKey(String empId, int year) {
        this.empId = empId;
        this.year = year;
    }
}
