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
		
		List<MasterCountDTO> masterCountDTOList = null;
		Iterator<MasterCountDTO> masterCountDTOIterator= null;
		
		List<SlaveCountDTO> slaveCountDTOList = null;
		Iterator<SlaveCountDTO> slaveCountDTOIterator= null;
		
		List<SlaveStatusDTO> slaveStatusDTOList = null;
		Iterator<SlaveStatusDTO> slaveStatusDTOIterator= null;
		
		/*
		 * get version
		 */
		
		mysqlVarialbeDTOList = this.findByVariableName("version");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		logger.debug("------------------------ version -------------------");
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			mysqlDTO.setMysqlVersion(mysqlVariableDTO.getValue());
		}
		
		
		/*
		 * get innodb version
		 */
		mysqlVarialbeDTOList = this.findByVariableName("innodb_version");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		logger.debug("------------------------ innodb version -------------------");
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			mysqlDTO.setInnodbVersion(mysqlVariableDTO.getValue());
		}
		
		/*
		 * get read_only
		 */
		mysqlVarialbeDTOList = this.findByVariableName("read_only");
		mysqlVarialbeDTOIterator = (Iterator<MySQLVariableDTO>) mysqlVarialbeDTOList.iterator();
		logger.debug("------------------------ read-only -------------------");
		while(mysqlVarialbeDTOIterator.hasNext()) {
			MySQLVariableDTO mysqlVariableDTO = (MySQLVariableDTO)mysqlVarialbeDTOIterator.next();
			logger.debug(mysqlVariableDTO.getVariableName());
			logger.debug(mysqlVariableDTO.getValue());
			
			if(mysqlVariableDTO.getValue().equals("OFF")) {
				mysqlDTO.setReadOnly(false);
			}else {
				mysqlDTO.setReadOnly(true);
			}
			
		}
		
		/*
		 * get master count
		 */
		masterCountDTOList = this.findMasterCount();
		masterCountDTOIterator = (Iterator<MasterCountDTO>) masterCountDTOList.iterator();
		logger.debug("------------------------ master count -------------------");
		while(masterCountDTOIterator.hasNext()) {
			MasterCountDTO masterCountDTO = (MasterCountDTO)masterCountDTOIterator.next();
			logger.debug(Integer.toString(masterCountDTO.getMasterCount()));
			
			mysqlDTO.setMasterActiveCount(masterCountDTO.getMasterCount());
			
		}
		
		
		/*
		 * get slave status
		 */
		slaveStatusDTOList = this.findSlaveStatus();
		slaveStatusDTOIterator = (Iterator<SlaveStatusDTO>) slaveStatusDTOList.iterator();
		logger.debug("------------------------ slave status -------------------");
		mysqlDTO.setMasterHostName("N/A");
		while(slaveStatusDTOIterator.hasNext()) {
			SlaveStatusDTO slaveStatusDTO = (SlaveStatusDTO)slaveStatusDTOIterator.next();
			logger.debug(slaveStatusDTO.getSlaveStatus());
			
			mysqlDTO.setMasterHostName(slaveStatusDTO.getSlaveStatus());
		}
		
		
		return mysqlDTO;
		
	}
	
	
	public List<SlaveStatusDTO> findSlaveStatus(){
		
		List<SlaveStatusDTO> result = jdbcTemplate.query(
                "show slave status",
                (rs, rowNum) -> new SlaveStatusDTO(rs.getString(2))
        );

        return result;
	}
	
	public List<SlaveCountDTO> findSlaveCount(){
		
		List<SlaveCountDTO> result = jdbcTemplate.query(
                "select count(*) as slave_count from processlist where COMMAND = 'Binlog Dump'",
                (rs, rowNum) -> new SlaveCountDTO(rs.getInt("slave_count"))
        );

        return result;
	}

	
	public List<MasterCountDTO> findMasterCount(){
		
		List<MasterCountDTO> result = jdbcTemplate.query(
                "select count(v1.user) as master_count from  (select user as user  from processlist where user = 'system user' group by user) v1",
                (rs, rowNum) -> new MasterCountDTO(rs.getInt("master_count"))
        );

        return result;
	}
	
	
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
