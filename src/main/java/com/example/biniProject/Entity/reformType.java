package com.example.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REFORM_TYPES")
public class reformType {
	
	@Id
	@Column(name="ID")
	private int reformId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="max_job_percent")
	private int maxJobPercent;

	public reformType() {
		super();
		// TODO Auto-generated constructor stub
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
