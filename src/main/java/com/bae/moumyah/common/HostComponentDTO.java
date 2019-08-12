package com.bae.moumyah.common;

public class HostComponentDTO {
	
	private String mysqlDirectory;
	private String tmpDirectory;
	private String ghostPostponeFlag;
	private String mysqldPid;
	private String mysqldSock;
	private String cmd;
	private String checkMysqld;
	private String checkGhost;
	private String checkGhostSock;
	public String getMysqlDirectory() {
		return mysqlDirectory;
	}
	public void setMysqlDirectory(String mysqlDirectory) {
		this.mysqlDirectory = mysqlDirectory;
	}
	public String getTmpDirectory() {
		return tmpDirectory;
	}
	public void setTmpDirectory(String tmpDirectory) {
		this.tmpDirectory = tmpDirectory;
	}
	public String getGhostPostponeFlag() {
		return ghostPostponeFlag;
	}
	public void setGhostPostponeFlag(String ghostPostponeFlag) {
		this.ghostPostponeFlag = ghostPostponeFlag;
	}
	public String getMysqldPid() {
		return mysqldPid;
	}
	public void setMysqldPid(String mysqldPid) {
		this.mysqldPid = mysqldPid;
	}
	public String getMysqldSock() {
		return mysqldSock;
	}
	public void setMysqldSock(String mysqldSock) {
		this.mysqldSock = mysqldSock;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getCheckMysqld() {
		return checkMysqld;
	}
	public void setCheckMysqld(String checkMysqld) {
		this.checkMysqld = checkMysqld;
	}
	public String getCheckGhost() {
		return checkGhost;
	}
	public void setCheckGhost(String checkGhost) {
		this.checkGhost = checkGhost;
	}
	public String getCheckGhostSock() {
		return checkGhostSock;
	}
	public void setCheckGhostSock(String checkGhostSock) {
		this.checkGhostSock = checkGhostSock;
	}
	@Override
	public String toString() {
		return "HostComponentDTO [mysqlDirectory=" + mysqlDirectory + ", tmpDirectory=" + tmpDirectory
				+ ", ghostPostponeFlag=" + ghostPostponeFlag + ", mysqldPid=" + mysqldPid + ", mysqldSock=" + mysqldSock
				+ ", cmd=" + cmd + ", checkMysqld=" + checkMysqld + ", checkGhost=" + checkGhost + ", checkGhostSock="
				+ checkGhostSock + "]";
	}
	
	

}
