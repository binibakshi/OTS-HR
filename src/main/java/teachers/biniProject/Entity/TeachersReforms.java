package teachers.biniProject.Entity;

import javax.persistence.*;

@Entity
@Table(name = "TEACHERS_REFORMS")
public class TeachersReforms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int Id;
	
	@Column(name="EMP_ID")
	private String empId;
	
	@Column(name="REFORM_TYPE")
	private int reformType;

	public TeachersReforms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeachersReforms(int id, String empId, int reformType) {
		super();
		Id = id;
		this.empId = empId;
		this.reformType = reformType;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getReformType() {
		return reformType;
	}

	public void setReformType(int reformType) {
		this.reformType = reformType;
	}	
	
}
