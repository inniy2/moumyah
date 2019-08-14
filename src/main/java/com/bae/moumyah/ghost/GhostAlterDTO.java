package com.bae.moumyah.ghost;

import java.util.ArrayList;

public class GhostAlterDTO {
	
	private String databaseName;
	
	private String tableName;
	
	private ArrayList<String> checkReplicaList;
	
	private ArrayList<String> alterStatement;

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

	public ArrayList<String> getCheckReplicaList() {
		return checkReplicaList;
	}

	public void setCheckReplicaList(ArrayList<String> checkReplicaList) {
		this.checkReplicaList = checkReplicaList;
	}

	public ArrayList<String> getAlterStatement() {
		return alterStatement;
	}

	public void setAlterStatement(ArrayList<String> alterStatement) {
		this.alterStatement = alterStatement;
	}

	@Override
	public String toString() {
		return "GhostAlterDTO [databaseName=" + databaseName + ", tableName=" + tableName + ", checkReplicaList="
				+ checkReplicaList + ", alterStatement=" + alterStatement + "]";
	}

	
	
	
	

}
