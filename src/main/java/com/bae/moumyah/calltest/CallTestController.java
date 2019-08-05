package com.bae.moumyah.calltest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/calltest")
public class CallTestController {

	
	@Autowired
	CallTestService callTestService;
	
	@GetMapping("/call2")
	public String getCall2() {
		return callTestService.call2();
		
	}
	
}
