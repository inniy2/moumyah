package com.bae.moumyah.common;

import org.springframework.stereotype.Component;

import com.bae.moumyah.schedule.HostDTO;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HostComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(HostComponent.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private OperatingSystemMXBean operationSystemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    
    
	public HostDTO getHostDTO(HostComponentDTO hostComponentDTO) {
		
		
		HostDTO hostDTO = new HostDTO();
		
		File mysqlDirectory    = new File(hostComponentDTO.getMysqlDirectory());
		File tempDirectory     = new File(hostComponentDTO.getTmpDirectory());
		File ghostPostponeFlag = new File(hostComponentDTO.getGhostPostponeFlag());
		File mysqlPid          = new File(hostComponentDTO.getMysqldPid());
		File mysqlSock         = new File(hostComponentDTO.getMysqldSock());
		
		hostDTO.setCpuPercentage(  this.getCpuPercentage());
			
		hostDTO.setTotalDiskSize(mysqlDirectory.getTotalSpace());
		hostDTO.setFreeDiskSize(mysqlDirectory.getUsableSpace());
		hostDTO.setMysqlDataSize(mysqlDirectory.getTotalSpace()- mysqlDirectory.getUsableSpace());		
		hostDTO.setFreeDiskPercentage((float)mysqlDirectory.getUsableSpace() / (float)mysqlDirectory.getTotalSpace() * 100);
		
		hostDTO.setGhostVersion(  this.getStringByShell("gh-ost --version"));
		hostDTO.setGhostSockCount(this.getFileCount(tempDirectory, hostComponentDTO.getCheckGhostSock()));
		hostDTO.setGhostPostponeFile(ghostPostponeFlag.exists());
		hostDTO.setGhostRunning(  this.getIsByShell("ps -ef","gh-ost"));
		
		hostDTO.setMysqlPid(mysqlPid.exists());
		hostDTO.setMysqlRunning(   this.getIsByShell("ps -ef","mysqld"));
		hostDTO.setMysqlSock(mysqlSock.exists());
		
		
		return hostDTO;
		
	}
	
	
	
	
	private float getCpuPercentage() {
		
    	Double processCpuLoad = operationSystemBean.getProcessCpuLoad() * 100;
    	Double systemCpuLoad  = operationSystemBean.getSystemCpuLoad() * 100;
    	Double systemLoadAverage = operationSystemBean.getSystemLoadAverage();
    	
    	Double totalCpuLoad = processCpuLoad + systemCpuLoad;
    	
        logger.debug("------------------------ DEBUG -------------------");
    	logger.debug(totalCpuLoad.toString());
    	
    	return Float.parseFloat(totalCpuLoad.toString());
    	
	}
	
	
	
	/*
	 * getStringByShell 
	 */
	private String getStringByShell(String cmd, String patternStr) {
    	
		Pattern pattern = Pattern.compile(patternStr);
		

		Runtime run = Runtime.getRuntime();
		Process pr;
		
		String returnValue = "";
		
		try {
			
			pr = run.exec(cmd);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			
			while((line=buf.readLine())!=null) {
				
				Matcher matcher = pattern.matcher(line);
				if(matcher.find()) {
					returnValue = line;
				}
				
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (returnValue.equals("")) {
				return "N/A";
			}else {
				return returnValue;
			}
		}
		
	}
	
	
	/*
	 * getStringByShell 
	 * example input is gh-ost --version
	 * return 1.0.48
	 */
	private String getStringByShell(String cmd) {
    	
		
		Runtime run = Runtime.getRuntime();
		Process pr;
		
		String returnValue = "";
		
		try {
			
			pr = run.exec(cmd);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			
			while((line=buf.readLine())!=null) {
				returnValue = returnValue + line;
			}
			
		}catch (IOException e) {
			
			return "N/A";
		}finally {
			
			if (returnValue.equals("")) {
				return "N/A";
			}else {
				return returnValue;
			}
			
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
	
	
	private boolean getIsByShell(String cmd) {
    	
		
		Runtime run = Runtime.getRuntime();
		Process pr;
		
		boolean returnValue = false;
		
		try {
			
			pr = run.exec(cmd);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			
			while((line=buf.readLine())!=null) {
				returnValue = true;
			}
			
		}catch (IOException e) {
			logger.debug(e.getMessage());
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
