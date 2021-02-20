package teachers.biniProject.HelperClasses;

public class GapsRewardHours {

    private String empId;
    private Double hours;
    private Double actualHours;
    private String firstName;
    private String lastName;

    public GapsRewardHours() {
        super();
    }

    public GapsRewardHours(String empId, Double hours, Double actualHours, String firstName, String lastName) {
        this.empId = empId;
        this.hours = hours;
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
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
