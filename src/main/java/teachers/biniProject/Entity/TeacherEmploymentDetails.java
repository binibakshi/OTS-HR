package teachers.biniProject.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHER_INFO")
public class TeacherEmploymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="record_key")
	private int Id;

	@Column(name="TZ")
	private String tz;

	@Column(name="BEGDA")
	private Date begda;

	@Column(name="ENDDA")
	private Date endda;

	@Column(name="MOSAD_ID")
	private int mosadId;

	@Column(name="EMPLOYMENT_CODE")
	private int empCode;

	@Column(name="DAY")
	private int day;

	@Column(name="STATUS")
	private char status;

	@Column(name="HOURS")
	private float hours;

	@Column(name="CREATE_BY")
	private String createBy;

	public TeacherEmploymentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TeacherEmploymentDetails(int id, String tz, Date begda, Date endda, int mosadId, int empCode, int day,
			float hours, String createBy) {
		super();
		Id = id;
		this.tz = tz;
		this.begda = begda;
		this.endda = endda;
		this.mosadId = mosadId;
		this.empCode = empCode;
		this.day = day;
		this.hours = hours;
		this.createBy = createBy;
	}

	public TeacherEmploymentDetails(TeacherEmploymentDetails teacherEmploymentDetails) {
		if(teacherEmploymentDetails != null) {
			Id = teacherEmploymentDetails.getId();
			this.tz = teacherEmploymentDetails.getTz();
			this.begda = teacherEmploymentDetails.getBegda();
			this.endda = teacherEmploymentDetails.getEndda();
			this.mosadId = teacherEmploymentDetails.getMosadId();
			this.empCode = teacherEmploymentDetails.getEmpCode();
			this.day = teacherEmploymentDetails.getDay();
			this.hours = teacherEmploymentDetails.getHours();
			this.createBy = teacherEmploymentDetails.getCreateBy();
		}
	}


	public TeacherEmploymentDetails(int id, String tz, Date begda, Date endda, int mosadId, int empCode, int day,
			char status, float hours, String createBy) {
		super();
		Id = id;
		this.tz = tz;
		this.begda = begda;
		this.endda = endda;
		this.mosadId = mosadId;
		this.empCode = empCode;
		this.day = day;
		this.status = status;
		this.hours = hours;
		this.createBy = createBy;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
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

	public int getMosadId() {
		return mosadId;
	}

	public void setMosadId(int mosadId) {
		this.mosadId = mosadId;
	}

	public int getEmpCode() {
		return empCode;
	}

	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


}
