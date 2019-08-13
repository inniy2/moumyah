package com.bae.moumyah.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MySQLVariableDTO {
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String variableName;
	
	private String value;
	
	
	
	public MySQLVariableDTO(String variableName, String value) {
		super();
		this.variableName = variableName;
		this.value = value;
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}



	@Override
	public String toString() {
		return "MySQLVariableDTO [id=" + id + ", variableName=" + variableName + ", value=" + value + "]";
	}
	
	
	

}
