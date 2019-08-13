package com.bae.moumyah.ghost;

public class GhostResultDTO {
	
	private String databaseName;
	
	private String tableName;
	
	
    private long id;
	
	private String fileName;
	
	private long tableFileSize;
	
	
	private String tableDescription;
	
	private long tableCadinality;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getTableFileSize() {
		return tableFileSize;
	}

	public void setTableFileSize(long tableFileSize) {
		this.tableFileSize = tableFileSize;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public long getTableCadinality() {
		return tableCadinality;
	}

	public void setTableCadinality(long tableCadinality) {
		this.tableCadinality = tableCadinality;
	}

	@Override
	public String toString() {
		return "GhostDTO [databaseName=" + databaseName + ", tableName=" + tableName + ", id=" + id + ", fileName="
				+ fileName + ", tableFileSize=" + tableFileSize + ", tableDescription=" + tableDescription
				+ ", tableCadinality=" + tableCadinality + "]";
	}
	

	
	
	

}
