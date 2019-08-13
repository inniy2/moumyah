package com.bae.moumyah.common;

public class MasterCountDTO {

	private int masterCount;

	
	
	public MasterCountDTO(int masterCount) {
		super();
		this.masterCount = masterCount;
	}

	public int getMasterCount() {
		return masterCount;
	}

	public void setMasterCount(int masterCount) {
		this.masterCount = masterCount;
	}

	@Override
	public String toString() {
		return "MasterCountDTO [masterCount=" + masterCount + "]";
	}
	
	
	
}
