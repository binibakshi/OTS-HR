package teachers.biniProject.HelperClasses;

public class HoursByEmpIdAndReform {

    private String empId;
    private Double hours;
    private int reformType;

    public HoursByEmpIdAndReform() {
    }

    public HoursByEmpIdAndReform(String empId, Double hours, int reformType) {
        this.empId = empId;
        this.hours = hours;
        this.reformType = reformType;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public int getReformType() {
        return reformType;
    }

    public void setReformType(int reformType) {
        this.reformType = reformType;
    }
}
