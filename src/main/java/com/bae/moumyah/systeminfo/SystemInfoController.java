package com.bae.moumyah.systeminfo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/systeminfo")
public class SystemInfoController {

	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	SystemInfoComponent systemInfoComponent;
	
	// https://www.callicoder.com/spring-boot-task-scheduling-with-scheduled-annotation/
	
	@Scheduled(fixedRateString = "${console.fetchMetrics}")
	public void scheduleTaskWithFixedRate() {
		
		systemInfoService.getCpuLoad();
		
	}
	

}
