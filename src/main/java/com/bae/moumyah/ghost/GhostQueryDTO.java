package com.bae.moumyah.ghost;

public class GhostQueryDTO {
	
	private String databaseName;
	
	private String tableName;

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

	@Override
	public String toString() {
		return "GhostQueryDTO [databaseName=" + databaseName + ", tableName=" + tableName + "]";
	}

	
}
