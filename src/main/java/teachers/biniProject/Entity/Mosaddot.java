package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOSSADOT")
public class Mosaddot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int Id;
	
	@Column(name="MOSAD_ID")
	private int mosadId;
	
	@Column(name="EMP_ID")
	private String empId;
	
	public Mosaddot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mosaddot(int id, int mosadId, String empId) {
		super();
		Id = id;
		this.mosadId = mosadId;
		this.empId = empId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getMosadId() {
		return mosadId;
	}

	public void setMosadId(int mosadId) {
		this.mosadId = mosadId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	
	
}
