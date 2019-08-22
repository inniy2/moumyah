package com.bae.moumyah.ghost;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ghost")
public class GhostController {
	
	
	@Autowired
	GhostService ghostService;
	
	
	/*
	@GetMapping("/validate/{tableName}")
	public ArrayList<String> getGhostValidateByTable(@PathVariable(value = "tableName") String tableName ) {
		
		return ghostService.getGhostValidateByTable(tableName);
	}
	

	@GetMapping("/validate")
	public GhostResultDTO getGhostValidate(@RequestParam("database_name")String databaseName, @RequestParam("table_name")String tableName) {
		
		GhostQueryDTO ghostQueryDTO = new GhostQueryDTO();
		
		ghostQueryDTO.setDatabaseName(databaseName);
		ghostQueryDTO.setTableName(tableName);
		
		return ghostService.getGhostDTO(ghostQueryDTO);
	}
	
	
	@PostMapping("/validate")
	public GhostResultDTO getGhostValidate(@Valid @RequestBody GhostQueryDTO ghostQueryDTO) {
	
		return ghostService.getGhostDTO(ghostQueryDTO);
	}
	
	*/
	
	
	
	
	@PostMapping("/validation")
	public GhostAlterDTO validation(@Valid @RequestBody GhostAlterDTO ghostAlterDTO) {
	
		return ghostService.validation(ghostAlterDTO);
	}
	
	
	@PostMapping("/dryrun")
	public GhostAlterDTO dryRun(@Valid @RequestBody GhostAlterDTO ghostAlterDTO) {
	
		return ghostService.ghostDryRun(ghostAlterDTO);
	}
	
	@PostMapping("/execute")
	public GhostAlterDTO execute(@Valid @RequestBody GhostAlterDTO ghostAlterDTO) {
	
		return ghostService.ghostExecute(ghostAlterDTO);
		
	}
	

}
