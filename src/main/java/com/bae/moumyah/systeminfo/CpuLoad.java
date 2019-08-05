package com.bae.moumyah.systeminfo;

public class CpuLoad {

	private Double processCpuLoad;
	private Double systemCpuLoad;
	
	public Double getProcessCpuLoad() {
		return processCpuLoad;
	}
	public void setProcessCpuLoad(Double processCpuLoad) {
		this.processCpuLoad = processCpuLoad;
	}
	public Double getSystemCpuLoad() {
		return systemCpuLoad;
	}
	public void setSystemCpuLoad(Double systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}
	
	@Override
	public String toString() {
		return "CpuLoad [processCpuLoad=" + processCpuLoad + ", systemCpuLoad=" + systemCpuLoad + "]";
	}
	
	
	
}
