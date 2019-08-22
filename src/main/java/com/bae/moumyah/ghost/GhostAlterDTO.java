package com.bae.moumyah.ghost;

import java.util.ArrayList;
import java.util.List;

public class GhostAlterDTO {
	
	private String databaseName;
	
	private String tableName;
	
	private ArrayList<String> checkReplicaList;
	
	private ArrayList<String> alterStatement;
	
	private int validationCode;
	
	private String validationMessage;
	
	private boolean isValicationPass;
	
	private List<String> outputStrList;

	
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

	public boolean isValicationPass() {
		return isValicationPass;
	}

	public void setValicationPass(boolean isValicationPass) {
		this.isValicationPass = isValicationPass;
	}
	
	

	public List<String> getOutputStrList() {
		return outputStrList;
	}

	public void setOutputStrList(List<String> outputStrList) {
		this.outputStrList = outputStrList;
	}

	@Override
	public String toString() {
		return "GhostAlterDTO [databaseName=" + databaseName + ", tableName=" + tableName + ", checkReplicaList="
				+ checkReplicaList + ", alterStatement=" + alterStatement + ", validationCode=" + validationCode
				+ ", validationMessage=" + validationMessage + ", isValicationPass=" + isValicationPass
				+ ", outputStrList=" + outputStrList + "]";
	}

	
	
	
}
