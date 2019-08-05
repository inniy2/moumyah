package com.bae.moumyah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoumyahApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoumyahApplication.class, args);
	}

}
