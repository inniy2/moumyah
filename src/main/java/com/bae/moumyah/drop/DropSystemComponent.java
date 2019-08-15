package com.bae.moumyah.drop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class DropSystemComponent {
	
	
	private static final Logger logger = LoggerFactory.getLogger(DropSystemComponent.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	
	
	public void runProcess(String[] cmd) {
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(cmd);
		
		try {
			
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			
			logger.info("------------------------------------------- inputstream");
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}
			
			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String errorLine = null;
			logger.info("------------------------------------------- errorstream");
			while ((errorLine = error.readLine()) != null) {
				logger.info(errorLine);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
