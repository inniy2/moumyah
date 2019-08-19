package com.bae.moumyah.drop;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drop")
public class DropController {

	
	@Autowired
	DropService dropService;

	
	@GetMapping("/getTargetTables")
	public List<DropDTO> getTargetTables() {
		return dropService.getTargetTables();	
	}
	
	
	@GetMapping("/createHardLink")
	public DropDTO createHardLink(@RequestParam("database_name")String databaseName, @RequestParam("table_name")String tableName) {
		
		DropDTO dropDTO = new DropDTO();
		dropDTO.setDatabaseName(databaseName);;
		dropDTO.setTableName(tableName);
	
		return dropService.createHardLink(dropDTO);
	}

	@PostMapping("/createHardLink")
	public DropDTO createHardLink(@Valid @RequestBody DropDTO dropDTO) {
		return dropService.createHardLink(dropDTO);
	}

	
	@GetMapping("/dropTable")
	public String dropTable(@RequestParam("database_name")String databaseName, @RequestParam("table_name")String tableName) {
		
		DropDTO dropDTO = new DropDTO();
		dropDTO.setDatabaseName(databaseName);;
		dropDTO.setTableName(tableName);
	
		return dropService.dropTable(dropDTO);
	}

	@PostMapping("/dropTable")
	public String dropTable(@Valid @RequestBody DropDTO dropDTO) {
		return dropService.dropTable(dropDTO);
	}
	
	
	
}
