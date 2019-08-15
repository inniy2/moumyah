package com.bae.moumyah.drop;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	
	
	public String createHardLink(DropDTO dropDTO) {
		
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
			return tableFileAbsolutePath + " is not exists."; 
		}
		
		/*
		 * Validation for hard link file
		 */
		File hardLink = new File(hardLinkFullPathStr);
		String hardLinkAbsolutePath = hardLink.getAbsolutePath();
		if(hardLink.exists()) {
			logger.info(hardLinkAbsolutePath + " already exists.");
			return hardLinkAbsolutePath + " already exists."; 
		} 
		
		
		logger.debug("targetFileFullPathStr : " + targetFileFullPathStr);
		logger.debug("hardLinkFullPathStr : " + hardLinkFullPathStr);
		
		/*
		 * Creating hard link
		 */
		String[] cmd = new String[] {"ln",targetFileFullPathStr,hardLinkFullPathStr};
		dropSystemComponent.runProcess(cmd);
		
		
		logger.debug("hardLinkAbsolutePath : " + hardLinkAbsolutePath);
		
		
		/*
		 * Return 
		 */
		if(hardLink.exists()) {
			logger.info(hardLinkAbsolutePath + " created.");
			return hardLinkAbsolutePath;
		} else {
			logger.info("Fail to create a hard link");
			return "Fail to create a hard link";
		}
		
		
	}
	
	
	
	
	public String dropTable(DropDTO dropDTO) {
		
		// Check read-only
		
		// Check hard link
		
		// drop table
		
		return "Table dropped";
	}
	
	
	
	public void truncateHardLink() {
		// TO-BO
	}

}
