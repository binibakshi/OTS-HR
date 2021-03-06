package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REFORM_TYPES")
public class ReformType {
	
	@Id
	@Column(name="ID")
	private int reformId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="max_job_percent")
	private int maxJobPercent;

	public ReformType() {
		super();
	}

	public int getReformId() {
		return reformId;
	}

	public String getName() {
		return name;
	}

	public int getmaxJobPercent() {
		return maxJobPercent;
	}
	
	
}
