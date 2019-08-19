package com.bae.moumyah.ghost;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
public class GhostSystemComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(GhostSystemComponent.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	
	private String ghostPostponeFlag = "/tmp/ghost.postpone.flag";
	private String tmpDirectory = "/tmp";
	
	
	public int validateBeforeRun() {
		

		int val = 0;
		
		int validationCode = 0;
		
		switch(val){
			case 0:
				validationCode++;   //code 1: postphone file
				File ghostPostponeFlag = new File(this.ghostPostponeFlag);
				if(ghostPostponeFlag.exists()) break;
			case 1:
				validationCode++;   //code 2: ghost process
				if(isGhostRunning("ps -ef", "gh-ost")) break;
			case 2:
				validationCode++;   //code 3: ghost sock files 
				File tempDirectory     = new File(this.tmpDirectory);
				if(getGhostSockCount(tempDirectory, "gh-ost.*.sock") > 0) break;
			default: 
				validationCode = 101;   //code 101: OK
		}
		
		logger.info("validationCode: "+ validationCode);
		
		return validationCode;
	}
	
	
	public boolean isGhostRunning(String cmd, String patternStr) {
		return this.getIsByShell(cmd, patternStr);
	}
	
	public int getGhostSockCount(File directory, String fileNamePattern) {
		return getFileCount(directory, fileNamePattern);
	}
		
	
	
	public List<String> runProcess(String[] cmd) {
		
		List<String> outputStrList = new ArrayList<String>();
		
		ProcessBuilder processBuilder = new ProcessBuilder();
			
		
		processBuilder.command(cmd);
		
		
		
		try {
			
			Process process = processBuilder.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			logger.info("------------------------------------------- inputstream");
			while ((line = reader.readLine()) != null) {
				logger.info(line);
				outputStrList.add(line);
			}
			
			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String errorLine = null;
			logger.info("------------------------------------------- errorstream");
			while ((errorLine = error.readLine()) != null) {
				logger.info(errorLine);
				outputStrList.add(errorLine);
			}
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			return outputStrList;
		}
		
		
	}
	
	
	
	private boolean getIsByShell(String cmd, String patternStr) {
    	
		Pattern pattern = Pattern.compile(patternStr);
		

		Runtime run = Runtime.getRuntime();
		Process pr;
		
		boolean returnValue = false;
		
		try {
			
			pr = run.exec(cmd);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			
			while((line=buf.readLine())!=null) {
				
				Matcher matcher = pattern.matcher(line);
				if(matcher.find()) {
					returnValue = true;
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			return returnValue;
		}
		
	}

	
	private int getFileCount(File directory, String fileNamePattern) {
		
		Pattern pattern = Pattern.compile(fileNamePattern);
		
		String children[] = directory.list();
		
		int cnt = 0;
		
		for(int i = 0; i < children.length; i++) {
			Matcher matcher = pattern.matcher(children[i]);
			if (matcher.find()) {
				cnt++;
			}
		}
		
		return cnt;
	}
	
	
}
