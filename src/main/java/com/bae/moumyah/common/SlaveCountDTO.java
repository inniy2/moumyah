package com.bae.moumyah.common;

public class SlaveCountDTO {

	private int slaveCount;

	public int getSlaveCount() {
		return slaveCount;
	}

	public void setSlaveCount(int slaveCount) {
		this.slaveCount = slaveCount;
	}

	public SlaveCountDTO(int slaveCount) {
		super();
		this.slaveCount = slaveCount;
	}

	@Override
	public String toString() {
		return "SlaveCountDTO [slaveCount=" + slaveCount + "]";
	}
	
	
	
}
