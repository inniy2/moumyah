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
	
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(2))
        );

        return result;
	}
	
	
	public List<String> findSlaveIORuningInSlaveStatus(){
		
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(11))
        );

        return result;
	}
	
	public List<String> findSlaveSQLRuningInSlaveStatus(){
		
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(12))
        );

        return result;
	}
	
	
	public List<String> findLastErrnoInSlaveStatus(){
		
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(19))
        );

        return result;
	}
	
	public List<String> findLastErrorInSlaveStatus(){
		
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(20))
        );

        return result;
	}
	
	public List<String> findSecondBehindMasterInSlaveStatus() throws Exception{
		
		String sql = "show slave status";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(33))
        );

        return result;
	}
	
	
	public List<Integer> findSlaveCount(){
		
		String sql = "select count(host) as slave_count from information_schema.processlist where 1=1 AND (COMMAND = 'Binlog Dump' OR COMMAND = 'Binlog Dump GTID') AND HOST NOT LIKE '127.0.0.1%' AND HOST NOT LIKE 'localhost%'";
		
		List<Integer> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Integer(rs.getInt("slave_count"))
        );

        return result;
	}
	
	public List<String> findSlaveHostName(){
		
		String sql = "select host from information_schema.processlist where 1=1 AND (COMMAND = 'Binlog Dump' OR COMMAND = 'Binlog Dump GTID') AND HOST NOT LIKE '127.0.0.1%' AND HOST NOT LIKE 'localhost%'";
		
		List<String> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> new String(rs.getString(1))
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
