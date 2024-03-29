package com.bae.moumyah.schedule;

public class MySQLDTO {
	
	private long id;

	private String reportHostName;
	
	private String mysqlVersion;
	
	private String innodbVersion;
	
	private Boolean readOnly;
	
	private int masterActiveCount;
	
	private int slaveCount;
	
	private String slaveHostName;
	
	private String masterHostName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReportHostName() {
		return reportHostName;
	}

	public void setReportHostName(String reportHostName) {
		this.reportHostName = reportHostName;
	}

	public String getMysqlVersion() {
		return mysqlVersion;
	}

	public void setMysqlVersion(String mysqlVersion) {
		this.mysqlVersion = mysqlVersion;
	}

	public String getInnodbVersion() {
		return innodbVersion;
	}

	public void setInnodbVersion(String innodbVersion) {
		this.innodbVersion = innodbVersion;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public int getMasterActiveCount() {
		return masterActiveCount;
	}

	public void setMasterActiveCount(int masterActiveCount) {
		this.masterActiveCount = masterActiveCount;
	}

	public int getSlaveCount() {
		return slaveCount;
	}

	public void setSlaveCount(int slaveCount) {
		this.slaveCount = slaveCount;
	}

	
	
	public String getSlaveHostName() {
		return slaveHostName;
	}

	public void setSlaveHostName(String slaveHostName) {
		this.slaveHostName = slaveHostName;
	}

	public String getMasterHostName() {
		return masterHostName;
	}

	public void setMasterHostName(String masterHostName) {
		this.masterHostName = masterHostName;
	}

	@Override
	public String toString() {
		return "MySQLDTO [id=" + id + ", reportHostName=" + reportHostName + ", mysqlVersion=" + mysqlVersion
				+ ", innodbVersion=" + innodbVersion + ", readOnly=" + readOnly + ", masterActiveCount="
				+ masterActiveCount + ", slaveCount=" + slaveCount + ", slaveHostName=" + slaveHostName
				+ ", masterHostName=" + masterHostName + "]";
	}

	
	
	
}
