package com.bae.moumyah.drop;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DropRepository {

	private static final Logger logger = LoggerFactory.getLogger(DropRepository.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public List<String[]> findDropTargetTables(){
		
		String sql = "select table_schema, table_name, (data_length + index_length + data_free) as data_length from information_schema.tables where table_name like '\\_%'";
		
		logger.debug("sql: "+sql);
		
		List<String[]> result = jdbcTemplate.query(sql,
					(rs, rowNum) -> new String[]{rs.getString("table_schema"), rs.getString("table_name"), rs.getString("data_length")}
		);
		
		return result;
		
	}
	
	public List<String[]> findDropTargetTableByName(String databaseName, String tableName){
		
		String sql = "select table_schema, table_name from information_schema.tables where table_schema ='"+databaseName+"' AND table_name = '"+tableName+"'";
		
		logger.debug("sql: "+sql);
		
		List<String[]> result = jdbcTemplate.query(sql,
					(rs, rowNum) -> new String[]{rs.getString("table_schema"), rs.getString("table_name")}
		);
		
		logger.debug("result size: "+ result.size());
		
		return result;
		
	}
	
	
	public void dropTable(String databaeName, String tableName) throws Exception{
			
		String sql = "drop table "+databaeName+"."+tableName;
		
		logger.debug("drop table sql: "+sql);
		
		jdbcTemplate.execute(sql);
		
				
	}
	
	
	public List<String[]> findByVariablesByName(String variableName){
		
		String sql ="SHOW VARIABLES LIKE \""+variableName+"\"";
		
		logger.debug("sql: "+sql);
		
		List<String[]> result = jdbcTemplate.query(sql,
				(rs, rowNum) -> new String[]{rs.getString("Variable_name"), rs.getString("Value")}
		);
				
		logger.debug("result size: "+ result.size());
		
		return result;
	}
	
	
}
