package com.bae.moumyah.drop;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DropService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(DropService.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Autowired
	DropRepository dropRepository;
	
	@Autowired
	DropSystemComponent dropSystemComponent;
	
	
	
	public List<DropDTO> getTargetTables(){
		
		/*
		 * create return object
		 */
		List<DropDTO> dropDTOList = new ArrayList<DropDTO>();
		
		
		/*
		 * SELECT SQL
		 */
		List<String[]> resultSet = dropRepository.findDropTargetTables();
		
		
		/*
		 * Set return object 
		 */
		resultSet.forEach( e -> dropDTOList.add(new DropDTO(e[0], e[1], new Long(e[2]))) ); // table_schema, table_name, data_length
		
		
		//dropDTOList.add(new DropDTO("bae_database","_tbl1_"));
		//dropDTOList.add(new DropDTO("bae_database","_tbl2_"));
		
		
		// https://dzone.com/articles/how-to-compare-list-objects-in-java-7-vs-java-8-1
		// System.out.println( dropDTOList.stream().anyMatch( e -> e.getTableName().contains("_tbl1_"))   );
		// dropDTOList.forEach(e -> System.out.println(e.getDatabaseName()));
		
		return  dropDTOList;
	}
	
	
	public DropDTO createHardLink(DropDTO dropDTO) {
		
		int validationCode = 0;
		String validationMessage = "N/A";
		
		/*
		 * Build file paths
		 */
		StringBuilder targetFileFullPath = new StringBuilder();
		StringBuilder hardLinkFullPath = new StringBuilder();
		
		targetFileFullPath.append(dropDTO.getMysqlDataDirectory());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getDatabaseName());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getTableName());
		targetFileFullPath.append(".ibd");
		
		hardLinkFullPath.append(dropDTO.getMysqlDataDirectory());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getDatabaseName());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getTableName());
		hardLinkFullPath.append(".ibd.rm");
		
		String targetFileFullPathStr = targetFileFullPath.toString();
		String hardLinkFullPathStr = hardLinkFullPath.toString();
		
		/*
		 * Validation for target table file
		 */
		File tableFile = new File(targetFileFullPathStr);
		String tableFileAbsolutePath = tableFile.getAbsolutePath();
		if(!tableFile.exists()) { 
			logger.info(tableFileAbsolutePath + " is not exists.");
			
			dropDTO.setValidationCode(1);
			dropDTO.setValidationMessage(tableFileAbsolutePath + " is not exists.");
			
			return dropDTO;
		}
		
		/*
		 * Validation for hard link file
		 */
		File hardLink = new File(hardLinkFullPathStr);
		String hardLinkAbsolutePath = hardLink.getAbsolutePath();
		if(hardLink.exists()) {
			logger.info(hardLinkAbsolutePath + " already exists.");
			
			dropDTO.setValidationCode(2);
			dropDTO.setValidationMessage(hardLinkAbsolutePath + " already exists.");
			
			return dropDTO;
			
		} 
		
		
		logger.debug("targetFileFullPathStr : " + targetFileFullPathStr);
		logger.debug("hardLinkFullPathStr : " + hardLinkFullPathStr);
		
		/*
		 * Creating hard link
		 */
		String[] cmd = new String[] {"ln",targetFileFullPathStr,hardLinkFullPathStr};
		dropSystemComponent.createHardLink(cmd);
		
		
		logger.debug("hardLinkAbsolutePath : " + hardLinkAbsolutePath);
		
		
		/*
		 * Return 
		 */
		if(hardLink.exists()) {
			logger.info(hardLinkAbsolutePath + " created.");
			
			dropDTO.setValidationCode(101);
			dropDTO.setValidationMessage(hardLinkAbsolutePath + " created.");
			
			return dropDTO;
			
		} else {
			logger.info("Fail to create a hard link");
			
			dropDTO.setValidationCode(9);
			dropDTO.setValidationMessage("Fail to create a hard link");
			
			return dropDTO;
		}
		
		
	}
	
	
	
	
	public DropDTO dropTable(DropDTO dropDTO) {
		
		List<String[]> resultSet = null;
		
		StringBuilder readOnly = new StringBuilder();
		
		/*
		 * Build file paths
		 */
		StringBuilder targetFileFullPath = new StringBuilder();
		StringBuilder hardLinkFullPath = new StringBuilder();
		
		targetFileFullPath.append(dropDTO.getMysqlDataDirectory());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getDatabaseName());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getTableName());
		targetFileFullPath.append(".ibd");
		
		hardLinkFullPath.append(dropDTO.getMysqlDataDirectory());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getDatabaseName());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getTableName());
		hardLinkFullPath.append(".ibd.rm");
		
		String targetFileFullPathStr = targetFileFullPath.toString();
		String hardLinkFullPathStr = hardLinkFullPath.toString();
		
		
		// Check read-only
		
		resultSet = dropRepository.findByVariablesByName("read_only");
		
		resultSet.forEach( e -> readOnly.append(e[1]) );
		
		if(!readOnly.toString().equals("OFF")) {
			logger.debug("variables read_only: "+readOnly.toString());
			dropDTO.setValidationCode(11);
			dropDTO.setValidationMessage("The host set to read_only: Drop table abort");
			logger.info("The host set to read_only: Drop table abort");
			return dropDTO;
		}
		
		logger.debug("variables read_only: "+readOnly.toString());
		
		
		// information schema and database name and table name
		
		resultSet = dropRepository.findDropTargetTableByName(dropDTO.getDatabaseName(), dropDTO.getTableName());
		
		if(resultSet.size() == 0 || resultSet.size() > 2) {
			
			logger.debug("findDropTargetTableByName size: "+resultSet.size());
			dropDTO.setValidationCode(12);
			dropDTO.setValidationMessage("Query to search drop target table size is " + resultSet.size() + ". Target table not found. Drop table abort");
			logger.info("Query to search drop target table size is " + resultSet.size() + ". Target table not found. Drop table abort");
			return dropDTO;
		}
		

		/*
		 * Validation for hard link file
		 */
		File hardLink = new File(hardLinkFullPathStr);
		String hardLinkAbsolutePath = hardLink.getAbsolutePath();
		if(!hardLink.exists()) {
			dropDTO.setValidationCode(2);
			dropDTO.setValidationMessage(hardLinkAbsolutePath + " is not exists. Drop abort");
			logger.info(hardLinkAbsolutePath + " is not exists. Drop abort");
			return dropDTO;
			
		} 
		
		// drop table
		
		try {
			
			dropRepository.dropTable(dropDTO.getDatabaseName(), dropDTO.getTableName());
			dropDTO.setValidationCode(101);
			dropDTO.setValidationMessage("Table dropped");
			logger.info("Table dropped: "+ targetFileFullPathStr);
		}catch( Exception e) {
			dropDTO.setValidationCode(18);
			dropDTO.setValidationMessage(e.getMessage());
		}finally {
			return dropDTO;
		}
		
		
	}
	
	
	
	public DropDTO truncateHardLink(DropDTO dropDTO) {
		
		/*
		 * Build file paths
		 */
		StringBuilder targetFileFullPath = new StringBuilder();
		StringBuilder hardLinkFullPath = new StringBuilder();
		
		targetFileFullPath.append(dropDTO.getMysqlDataDirectory());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getDatabaseName());
		targetFileFullPath.append("/");
		targetFileFullPath.append(dropDTO.getTableName());
		targetFileFullPath.append(".ibd");
		
		hardLinkFullPath.append(dropDTO.getMysqlDataDirectory());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getDatabaseName());
		hardLinkFullPath.append("/");
		hardLinkFullPath.append(dropDTO.getTableName());
		hardLinkFullPath.append(".ibd.rm");
		
		String targetFileFullPathStr = targetFileFullPath.toString();
		String hardLinkFullPathStr = hardLinkFullPath.toString();
		
		
		/*
		 * Validation for target table file
		 */
		File tableFile = new File(targetFileFullPathStr);
		String tableFileAbsolutePath = tableFile.getAbsolutePath();
		if(tableFile.exists()) { 
			logger.info(tableFileAbsolutePath + " is exists. Truncate abort");
			
			dropDTO.setValidationCode(1);
			dropDTO.setValidationMessage(tableFileAbsolutePath + " is exists. Truncate abort.");
			
			return dropDTO;
		}
		
		/*
		 * Validation for hard link file
		 */
		File hardLink = new File(hardLinkFullPathStr);
		String hardLinkAbsolutePath = hardLink.getAbsolutePath();
		if(!hardLink.exists()) {
			logger.info(hardLinkAbsolutePath + " is not exists. Truncate abort");
			
			dropDTO.setValidationCode(2);
			dropDTO.setValidationMessage(hardLinkAbsolutePath + " is not exists. Truncate abort");
			
			return dropDTO;
			
		} 
		
		
		logger.info("Hard link full path   : " + hardLinkFullPathStr);
		
		String reduceBy = "-"+dropDTO.getTruncateSize();
		
		String[] cmdTruncate = new String[] {"truncate","-s",reduceBy,hardLinkFullPathStr};
		
		String[] cmdRemove = new String[] {"rm","-rf",hardLinkFullPathStr};
		
		Long hardLinkFileSize = 0L;
		
		
		int truncateLoopCount = 0;
		
		while(true) {
			
			Long truncateBaseSize = dropDTO.getTruncateSize() + 314572800;
			hardLinkFileSize = hardLink.length();
			
			logger.info("truncate loop count : " + ++truncateLoopCount);
			logger.info("truncate base size : " + truncateBaseSize);
			logger.info("hard link length   : " + hardLinkFileSize);
			
			
			
			if(hardLinkFileSize <= truncateBaseSize ) {
				logger.info("Remove : " + hardLinkFullPathStr);
				// Remove hard link
				dropSystemComponent.truncateFile(cmdRemove);
				dropDTO.setValidationCode(101);
				dropDTO.setValidationMessage("Hard link removed.");
				dropDTO.setTruncateLoopCount(truncateLoopCount);
				break;
			}
			
			
			// truncate hard link
			dropSystemComponent.truncateFile(cmdTruncate);
			dropDTO.setValidationCode(29);
			dropDTO.setValidationMessage("Hard link truncated.");
			dropDTO.setTruncateLoopCount(1);
			logger.info("truncated : " + hardLinkFullPathStr);
			
			try {
				Thread.sleep(1000 * dropDTO.getTruncateInterval());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return dropDTO;
	}
	
}
