package com.bae.moumyah.ghost;

public class GhostMySQLDTO {

	private long id;
	
	private String tableDescription;
	
	private long tableCadinality;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "GhostMySQLDTO [id=" + id + ", tableDescription=" + tableDescription + ", tableCadinality="
				+ tableCadinality + "]";
	}
	
	
	
}
