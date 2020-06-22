package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEACHERS_REFORMS")
public class TeachersReforms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int Id;
	
	@Column(name="TZ")
	private String tz;
	
	@Column(name="REFORM_TYPE")
	private int reformType;

	public TeachersReforms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeachersReforms(String tz, int reformType) {
		super();
		this.tz = tz;
		this.reformType = reformType;
	}

	public TeachersReforms(int id, String tz, int reformType) {
		super();
		Id = id;
		this.tz = tz;
		this.reformType = reformType;
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

	public int getReformType() {
		return reformType;
	}

	public void setReformType(int reformType) {
		this.reformType = reformType;
	}
	
}
