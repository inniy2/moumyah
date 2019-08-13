package com.bae.moumyah.schedule;

import java.time.format.DateTimeFormatter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class ScheduleRepository {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleRepository.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	
	public List<String> findMasterHostInSlaveStatus(){
		
		List<String> result = jdbcTemplate.query(
                "show slave status",
                (rs, rowNum) -> new String(rs.getString(2))
        );

        return result;
	}
	
	public List<Integer> findSlaveCount(){
		
		List<Integer> result = jdbcTemplate.query(
                "select count(*) as slave_count from processlist where COMMAND = 'Binlog Dump'",
                (rs, rowNum) -> new Integer(rs.getInt("slave_count"))
        );

        return result;
	}

	
	public List<Integer> findMasterCount(){
		
		List<Integer> result = jdbcTemplate.query(
                "select count(v1.user) as master_count from  (select user as user  from processlist where user = 'system user' group by user) v1",
                (rs, rowNum) -> new Integer(rs.getInt("master_count"))
        );

        return result;
	}
	
	
	public List<String> findByVariableByName(String variableName){
		
		List<String> result = jdbcTemplate.query(
                "SHOW VARIABLES LIKE \""+variableName+"\"",
                (rs, rowNum) -> new String(rs.getString("Value"))
        );

        return result;
		
	}
	
	public List<String[]> findByVariablesByName(String variableName){
		
		List<String[]> result = jdbcTemplate.query(
                "SHOW VARIABLES LIKE \"%"+variableName+"%\"",
                (rs, rowNum) -> new String[]{rs.getString("Variable_name"), rs.getString("Value")}
        );

        return result;
		
	}
	
}
