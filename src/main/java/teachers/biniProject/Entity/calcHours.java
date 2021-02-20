package teachers.biniProject.Entity;


import javax.persistence.*;


@Entity
@Table(name = "CALC_HOURS")
public class CalcHours {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Id;
	
	@Column(name="REFORM_TYPE")
	private int reformType;
	
	@Column(name="IS_MOTHER")
	private boolean isMother;
	
	@Column(name="AGE_HOURS")
	private int ageHours;
	
	@Column(name="FRONTAL")
	private float frontalHours;
	
	@Column(name="PRIVATE")
	private float privateHours;
	
	@Column(name="PAUSE")
	private float pauseHours;
	
	@Column(name="JOB_PERCENT")
	private float jobPercent;
	
	public CalcHours() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalcHours(int reformType, boolean isMother, int ageHours, float frontalHours, float privateHours,
					 float pauseHours, float jobPercent) {
		super();
		this.reformType = reformType;
		this.isMother = isMother;
		this.ageHours = ageHours;
		this.frontalHours = frontalHours;
		this.privateHours = privateHours;
		this.pauseHours = pauseHours;
		this.jobPercent = jobPercent;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getReformType() {
		return reformType;
	}

	public void setReformType(int reformType) {
		this.reformType = reformType;
	}

	public boolean isMother() {
		return isMother;
	}

	public void setMother(boolean isMother) {
		this.isMother = isMother;
	}

	public int getAgeHours() {
		return ageHours;
	}

	public void setAgeHours(int ageHours) {
		this.ageHours = ageHours;
	}

	public float getFrontalHours() {
		return frontalHours;
	}

	public void setFrontalHours(float frontalHours) {
		this.frontalHours = frontalHours;
	}

	public float getPrivateHours() {
		return privateHours;
	}

	public void setPrivateHours(float privateHours) {
		this.privateHours = privateHours;
	}

	public float getPauseHours() {
		return pauseHours;
	}

	public void setPauseHours(float pauseHours) {
		this.pauseHours = pauseHours;
	}

	public float getJobPercent() {
		return jobPercent;
	}

	public void setJobPercent(float jobPercent) {
		this.jobPercent = jobPercent;
	}

}
