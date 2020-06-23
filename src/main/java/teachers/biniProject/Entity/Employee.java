package teachers.biniProject.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "EMPLOYEES")
public class Employee {

	@Id
	@Column(name="EMP_ID")
	private String empId;
	
	@Column(name="BEGDA")
	private Date begda;
	
	@Column(name="ENDDA")
	private Date endda;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="BIRTH_DATE")
	private Date birthDate;

	@Column(name="GENDER")
	private char gender;

	@Column(name="IS_MOTHER")
	private boolean isMother;
	
	@Column(name="REFORM_TYPE")
	private int reform_type;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Column(name="STATUS")
	private char status;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String empId, Date begda, Date endda, String firstName, String lastName, Date birthDate,
			char gender, boolean isMother, int reform_type, String createBy, char status) {
		super();
		this.empId = empId;
		this.begda = begda;
		this.endda = endda;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.isMother = isMother;
		this.reform_type = reform_type;
		this.createBy = createBy;
		this.status = status;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getBegda() {
		return begda;
	}

	public void setBegda(Date begda) {
		this.begda = begda;
	}

	public Date getEndda() {
		return endda;
	}

	public void setEndda(Date endda) {
		this.endda = endda;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public boolean isMother() {
		return isMother;
	}

	public void setMother(boolean isMother) {
		this.isMother = isMother;
	}

	public int getReform_type() {
		return reform_type;
	}

	public void setReform_type(int reform_type) {
		this.reform_type = reform_type;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	

	
	
}
