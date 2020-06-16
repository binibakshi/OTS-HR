package com.example.biniProject.Entity;

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
	
	@Column(name="TZ")
	private String tz;
	
	public Mosaddot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mosaddot(int mosadId, String tz) {
		super();
		this.mosadId = mosadId;
		this.tz = tz;
	}

	public int getMosadId() {
		return mosadId;
	}

	public void setMosadId(int mosadId) {
		this.mosadId = mosadId;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}



	
	
	
	
	
	
}
