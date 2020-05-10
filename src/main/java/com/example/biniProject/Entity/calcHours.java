package com.example.biniProject.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "CALC_HOURS")
public class calcHours {
	
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
	private int frontalHours;
	
	@Column(name="PRIVATE")
	private int privateHours;
	
	@Column(name="PAUSE")
	private int pauseHours;
	
	@Column(name="JOB_PERCENT")
	private float jobPercent;
	
	public calcHours() {
		super();
		// TODO Auto-generated constructor stub
	}

	public calcHours(int reformType, boolean isMother, int ageHours, int frontalHours, int privateHours,
			int pauseHours, float jobPercent) {
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

	public int getFrontalHours() {
		return frontalHours;
	}

	public void setFrontalHours(int frontalHours) {
		this.frontalHours = frontalHours;
	}

	public int getPrivateHours() {
		return privateHours;
	}

	public void setPrivateHours(int privateHours) {
		this.privateHours = privateHours;
	}

	public int getPauseHours() {
		return pauseHours;
	}

	public void setPauseHours(int pauseHours) {
		this.pauseHours = pauseHours;
	}

	public float getJobPercent() {
		return jobPercent;
	}

	public void setJobPercent(float jobPercent) {
		this.jobPercent = jobPercent;
	}

		
	
}
