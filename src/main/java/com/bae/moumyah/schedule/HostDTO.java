package com.bae.moumyah.schedule;

public class HostDTO {
	
	
	private long id;

	private String clusterName;
	
	private float cpuPercentage;

	private float freeDiskPercentage;
	
	private long totalDiskSize;

	private long freeDiskSize;
	
	private long mysqlDataSize;
	
	private String ghostVersion;
	
	private int ghostSockCount;
	
    private boolean ghostPostponeFile;
    
    private boolean ghostRunning;
    
    private boolean mysqlPid;
    
    private boolean mysqlRunning;
    
    private boolean mysqlSock;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public float getCpuPercentage() {
		return cpuPercentage;
	}

	public void setCpuPercentage(float cpuPercentage) {
		this.cpuPercentage = cpuPercentage;
	}

	public float getFreeDiskPercentage() {
		return freeDiskPercentage;
	}

	public void setFreeDiskPercentage(float freeDiskPercentage) {
		this.freeDiskPercentage = freeDiskPercentage;
	}

	public long getTotalDiskSize() {
		return totalDiskSize;
	}

	public void setTotalDiskSize(long totalDiskSize) {
		this.totalDiskSize = totalDiskSize;
	}

	public long getFreeDiskSize() {
		return freeDiskSize;
	}

	public void setFreeDiskSize(long freeDiskSize) {
		this.freeDiskSize = freeDiskSize;
	}

	public long getMysqlDataSize() {
		return mysqlDataSize;
	}

	public void setMysqlDataSize(long mysqlDataSize) {
		this.mysqlDataSize = mysqlDataSize;
	}

	public String getGhostVersion() {
		return ghostVersion;
	}

	public void setGhostVersion(String ghostVersion) {
		this.ghostVersion = ghostVersion;
	}

	public int getGhostSockCount() {
		return ghostSockCount;
	}

	public void setGhostSockCount(int ghostSockCount) {
		this.ghostSockCount = ghostSockCount;
	}

	public boolean isGhostPostponeFile() {
		return ghostPostponeFile;
	}

	public void setGhostPostponeFile(boolean ghostPostponeFile) {
		this.ghostPostponeFile = ghostPostponeFile;
	}

	public boolean isGhostRunning() {
		return ghostRunning;
	}

	public void setGhostRunning(boolean ghostRunning) {
		this.ghostRunning = ghostRunning;
	}

	public boolean isMysqlPid() {
		return mysqlPid;
	}

	public void setMysqlPid(boolean mysqlPid) {
		this.mysqlPid = mysqlPid;
	}

	public boolean isMysqlRunning() {
		return mysqlRunning;
	}

	public void setMysqlRunning(boolean mysqlRunning) {
		this.mysqlRunning = mysqlRunning;
	}

	public boolean isMysqlSock() {
		return mysqlSock;
	}

	public void setMysqlSock(boolean mysqlSock) {
		this.mysqlSock = mysqlSock;
	}

	@Override
	public String toString() {
		return "HostDTO [id=" + id + ", clusterName=" + clusterName + ", cpuPercentage=" + cpuPercentage
				+ ", freeDiskPercentage=" + freeDiskPercentage + ", totalDiskSize=" + totalDiskSize + ", freeDiskSize="
				+ freeDiskSize + ", mysqlDataSize=" + mysqlDataSize + ", ghostVersion=" + ghostVersion
				+ ", ghostSockCount=" + ghostSockCount + ", ghostPostponeFile=" + ghostPostponeFile + ", ghostRunning="
				+ ghostRunning + ", mysqlPid=" + mysqlPid + ", mysqlRunning=" + mysqlRunning + ", mysqlSock="
				+ mysqlSock + "]";
	}
    
    
	
	

}
