package com.bae.moumyah.getosinfo;

public class HostDTO {
	
	
	private long id;

	private float cpuPercentage;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getCpuPercentage() {
		return cpuPercentage;
	}

	public void setCpuPercentage(float cpuPercentage) {
		this.cpuPercentage = cpuPercentage;
	}

	
	@Override
	public String toString() {
		return "Host [id=" + id + ", cpuPercentage=" + cpuPercentage + "]";
	}

}
