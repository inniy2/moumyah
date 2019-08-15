package com.bae.moumyah.drop;

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
		
		List<String[]> result = jdbcTemplate.query(
				"select table_schema, table_name, (data_length + index_length + data_free) as data_length from information_schema.tables where table_name like '\\_%'",
					(rs, rowNum) -> new String[]{rs.getString("table_schema"), rs.getString("table_name"), rs.getString("data_length")}
		);
		
		return result;
		
	}
	
	
	
	
}
