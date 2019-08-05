package com.bae.moumyah.getosinfo;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/cluster")
public class HostController {
	

	@Autowired
	HostService hostService;
	
	
   
	

}
