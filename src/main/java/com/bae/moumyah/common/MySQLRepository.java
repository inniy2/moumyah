package com.bae.moumyah.common;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bae.moumyah.schedule.MySQLDTO;

@Repository
public class MySQLRepository {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MySQLRepository.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	
	public MySQLDTO getMySQLDTO() {
		
		
		MySQLDTO mysqlDTO = new MySQLDTO();
		
		
		List<MySQLVariableDTO> mysqlVarialbeDTOList = null;
		Iterator<MySQLVariableDTO> mysqlVarialbeDTOIterator = null;
		
		/*
		 * get version
		 */
		
		mysqlVarialbeDTOList = this.findByVariableName("version");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			logger.debug("------------------------ DEBUG -------------------");
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			mysqlDTO.setMysqlVersion(mysqlVariableDTO.getValue());
		}
		
		
		/*
		 * get innodb version
		 */
		mysqlVarialbeDTOList = this.findByVariableName("innodb_version");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			logger.debug("------------------------ DEBUG -------------------");
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			mysqlDTO.setInnodbVersion(mysqlVariableDTO.getValue());
		}
		
		/*
		 * get read_only
		 */
		mysqlVarialbeDTOList = this.findByVariableName("read_only");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			logger.debug("------------------------ DEBUG -------------------");
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			if(mysqlVariableDTO.getValue().equals("OFF")) {
				mysqlDTO.setReadOnly(false);
			}else {
				mysqlDTO.setReadOnly(true);
			}
			
		}
		
		mysqlDTO.setMasterActiveCount(0);
		mysqlDTO.setSlaveCount(3);
		mysqlDTO.setMasterHostName("N/A");
		

		
		return mysqlDTO;
		
	}
	
	
	
	// TO-DO method for count masters
	
	
	public List<MySQLVariableDTO> findByVariableName(String variableName){
		
		List<MySQLVariableDTO> result = jdbcTemplate.query(
                "SHOW VARIABLES LIKE \""+variableName+"\"",
                (rs, rowNum) -> new MySQLVariableDTO(rs.getString("Variable_name"), rs.getString("Value"))
        );

        return result;
		
	}
	
	public List<MySQLVariableDTO> findByVariableNames(String variableName){
		
		List<MySQLVariableDTO> result = jdbcTemplate.query(
                "SHOW VARIABLES LIKE \"%"+variableName+"%\"",
                (rs, rowNum) -> new MySQLVariableDTO(rs.getString("Variable_name"), rs.getString("Value"))
        );

        return result;
		
	}
	
}
