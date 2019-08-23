package com.bae.moumyah.schedule;

public class TableDTO {
	
	private long id;

	private String clusterName;
	
	private String hostName;
	
	private String tableSchema;
	
	private String tableName;
	
	private long dataLength;
	
    private long dataTimestamp;
    
    

	public TableDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public TableDTO(long id, String clusterName, String hostName, String tableSchema, String tableName, long dataLength,long dataTimestamp) {
		super();
		this.id = id;
		this.clusterName = clusterName;
		this.hostName = hostName;
		this.tableSchema = tableSchema;
		this.tableName = tableName;
		this.dataLength = dataLength;
		this.dataTimestamp = dataTimestamp;
	}




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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
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

	public long getDataTimestamp() {
		return dataTimestamp;
	}

	public void setDataTimestamp(long dataTimestamp) {
		this.dataTimestamp = dataTimestamp;
	}

	@Override
	public String toString() {
		return "TableDTO [id=" + id + ", clusterName=" + clusterName + ", hostName=" + hostName + ", tableSchema="
				+ tableSchema + ", tableName=" + tableName + ", dataLength=" + dataLength + ", dataTimestamp="
				+ dataTimestamp + "]";
	}
	
    
    

}
