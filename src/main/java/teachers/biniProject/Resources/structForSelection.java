package teachers.biniProject.Resources;

import java.util.Date;

public class structForSelection {

	private String empId;

	private int mossadId;

	private Date birthdate;

	private char gender;

	private boolean isMother;

	private Date minStartDate;

	private Date maxStartDate;

	private Date minEndDate;

	private Date maxEndDate;
	
	private String name;
	
	private int schoolYear;

	public structForSelection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public structForSelection(String empId, int mossadId, Date birthdate, char gender, boolean isMother,
			Date minStartDate, Date maxStartDate, Date minEndDate, Date maxEndDate, String name, int schoolYear) {
		super();
		this.empId = empId;
		this.mossadId = mossadId;
		this.birthdate = birthdate;
		this.gender = gender;
		this.isMother = isMother;
		this.minStartDate = minStartDate;
		this.maxStartDate = maxStartDate;
		this.minEndDate = minEndDate;
		this.maxEndDate = maxEndDate;
		this.name = name;
		this.schoolYear = schoolYear;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getMossadId() {
		return mossadId;
	}

	public void setMossadId(int mossadId) {
		this.mossadId = mossadId;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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

	public Date getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}

	public Date getMaxStartDate() {
		return maxStartDate;
	}

	public void setMaxStartDate(Date maxStartDate) {
		this.maxStartDate = maxStartDate;
	}

	public Date getMinEndDate() {
		return minEndDate;
	}

	public void setMinEndDate(Date minEndDate) {
		this.minEndDate = minEndDate;
	}

	public Date getMaxEndDate() {
		return maxEndDate;
	}

	public void setMaxEndDate(Date maxEndDate) {
		this.maxEndDate = maxEndDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	
	
}
