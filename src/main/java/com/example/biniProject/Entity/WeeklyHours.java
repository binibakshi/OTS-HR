package com.example.biniProject.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEEKLY_HOURS")
public class WeeklyHours {
	
	@Id
	@Column(name="RECORD_KEY")
	private int recordKey;
	
	@Column(name="TZ")
	private String tz;
	
	@Column(name="BEGDA")
	private Date begda;
	
	@Column(name="ENDDA")
	private Date endda;
	
	@Column(name="MOSAD_ID")
	private int mosadId;
	
	@Column(name="EMPLOYNET_CODE")
	private int employmentCode;
	
	@Column(name="DAY")
	private int day;
	
	@Column(name="FRONTAL")
	private float frontalHours;
	
	@Column(name="PRIVATE")
	private float privateHours;
	
	@Column(name="PAUSE")
	private float pauseHours;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Column(name="JOB_PERCENT")
	private float jobPercent;

	public WeeklyHours() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeeklyHours(String tz, Date begda, Date endda, int mosadId, int employmentCode, int day,
			float frontalHours, float privateHours, float pauseHours, String createBy, float jobPercent) {
		super();
		this.tz = tz;
		this.begda = begda;
		this.endda = endda;
		this.mosadId = mosadId;
		this.employmentCode = employmentCode;
		this.day = day;
		this.frontalHours = frontalHours;
		this.privateHours = privateHours;
		this.pauseHours = pauseHours;
		this.createBy = createBy;
		this.jobPercent = jobPercent;
	}

	public int getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(int recordKey) {
		this.recordKey = recordKey;
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

	public int getEmploymentCode() {
		return employmentCode;
	}

	public void setEmploymentCode(int employmentCode) {
		this.employmentCode = employmentCode;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public float getJobPercent() {
		return jobPercent;
	}

	public void setJobPercent(float jobPercent) {
		this.jobPercent = jobPercent;
	}
	
	
	
}
