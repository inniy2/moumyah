package com.bae.moumyah.ghost;

public class GhostHostDTO {

	private long id;
	
	private String fileName;
	
	private long tableFileSize;

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

	@Override
	public String toString() {
		return "GhostHostDTO [id=" + id + ", fileName=" + fileName + ", tableFileSize=" + tableFileSize + "]";
	}
	
	
	
}
