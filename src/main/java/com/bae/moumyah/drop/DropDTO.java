package com.bae.moumyah.drop;

public class DropDTO {

	private String mysqlDataDirectory;
	
	private String databaseName;
	private String tableName;
	private long dataLength;
	
	
	private int validationCode;
	private String validationMessage;
	
	private int truncateInterval = 10;
	private long truncateSize = 314572800;
	private int truncateLoopCount;
	
	
	public DropDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	/*
	 * To Set return object 
	 */
	public DropDTO(String databaseName, String tableName, long dataLength) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.dataLength = dataLength;
	}




	public DropDTO(String mysqlDataDirectory, String databaseName, String tableName, long dataLength) {
		super();
		this.mysqlDataDirectory = mysqlDataDirectory;
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.dataLength = dataLength;
	}


	public String getMysqlDataDirectory() {
		return mysqlDataDirectory;
	}


	public void setMysqlDataDirectory(String mysqlDataDirectory) {
		this.mysqlDataDirectory = mysqlDataDirectory;
	}


	public String getDatabaseName() {
		return databaseName;
	}


	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public long getDataLength() {
		return dataLength;
	}


	public void setDataLength(long dataLength) {
		this.dataLength = dataLength;
	}

	
	

	public int getValidationCode() {
		return validationCode;
	}



	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}



	public String getValidationMessage() {
		return validationMessage;
	}



	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}


	

	public int getTruncateInterval() {
		return truncateInterval;
	}



	public void setTruncateInterval(int truncateInterval) {
		this.truncateInterval = truncateInterval;
	}



	public long getTruncateSize() {
		return truncateSize;
	}



	public void setTruncateSize(long truncateSize) {
		this.truncateSize = truncateSize;
	}

	

	public int getTruncateLoopCount() {
		return truncateLoopCount;
	}



	public void setTruncateLoopCount(int truncateLoopCount) {
		this.truncateLoopCount = truncateLoopCount;
	}



	@Override
	public String toString() {
		return "DropDTO [mysqlDataDirectory=" + mysqlDataDirectory + ", databaseName=" + databaseName + ", tableName="
				+ tableName + ", dataLength=" + dataLength + ", validationCode=" + validationCode
				+ ", validationMessage=" + validationMessage + ", truncateInterval=" + truncateInterval
				+ ", truncateSize=" + truncateSize + ", truncateLoopCount=" + truncateLoopCount + "]";
	}



	
}
