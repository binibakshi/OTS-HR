package teachers.biniProject.HelperClasses;

public class GapsTeacherHours {

    private String empId;
    private Double estimateHours;
    private Double actualHours;
    private String firstName;
    private String lastName;

    public GapsTeacherHours() {
        super();
    }

    public GapsTeacherHours(String empId, Double estimateHours, Double actualHours, String firstName, String lastName) {
        this.empId = empId;
        this.estimateHours = estimateHours;
        this.actualHours = actualHours;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Double getEstimateHours() {
        return estimateHours;
    }

    public void setEstimateHours(Double estimateHours) {
        this.estimateHours = estimateHours;
    }

    public Double getActualHours() {
        return actualHours;
    }

    public void setActualHours(Double actualHours) {
        this.actualHours = actualHours;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
