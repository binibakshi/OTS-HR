package teachers.biniProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "convert_hour_type")
public class ConvertHours {

	@Id
	@Column(name="CODE")
	private int code;

	@Column(name="REFORM_TYPE")
	private int reformType;

	@Column(name="CODE_DESC")
	private String codeDescription;
	
	// 0-administration employment 
    // 1-frontal
	// 2-private
	// 3-pause
	@Column(name="HOUR_TYPE")
	private int hourType;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getReformType() {
		return reformType;
	}

	public void setReformType(int reformType) {
		this.reformType = reformType;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public int getHourType() {
		return hourType;
	}

	public void setHourType(int hourType) {
		this.hourType = hourType;
	}
	
	
}
