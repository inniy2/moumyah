package com.bae.moumyah.validate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

	@GetMapping("/isup")
	public String isServiceUP() {
		
		return "is up";
	}
	
}
