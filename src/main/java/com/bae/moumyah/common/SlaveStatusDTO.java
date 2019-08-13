package com.bae.moumyah.common;

public class SlaveStatusDTO {
	
	private String slaveStatus;

	
	public SlaveStatusDTO(String slaveStatus) {
		super();
		this.slaveStatus = slaveStatus;
	}

	
	public String getSlaveStatus() {
		return slaveStatus;
	}

	public void setSlaveStatus(String slaveStatus) {
		this.slaveStatus = slaveStatus;
	}


	@Override
	public String toString() {
		return "SlaveStatusDTO [slaveStatus=" + slaveStatus + "]";
	}
	
	
	

}
