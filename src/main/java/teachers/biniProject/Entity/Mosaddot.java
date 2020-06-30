package teachers.biniProject.Entity;

import javax.persistence.*;

@Entity
@Table(name = "MOSSADOT")
public class Mosaddot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int Id;
	
	@Column(name="MOSAD_ID")
	private int mossadId;
	
	@Column(name="EMP_ID")
	private String empId;
	
	public Mosaddot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mosaddot(int id, int mossadId, String empId) {
		super();
		Id = id;
		this.mossadId = mossadId;
		this.empId = empId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getMossadId() {
		return mossadId;
	}

	public void setMossadId(int mossadId) {
		this.mossadId = mossadId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	
	
}
