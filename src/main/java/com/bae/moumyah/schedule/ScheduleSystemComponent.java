package com.bae.moumyah.schedule;

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
public class ScheduleSystemComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleSystemComponent.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private OperatingSystemMXBean operationSystemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    
    
	
	public float getCpuPercentage() {
		
    	Double processCpuLoad = operationSystemBean.getProcessCpuLoad() * 100;
    	Double systemCpuLoad  = operationSystemBean.getSystemCpuLoad() * 100;
    	Double systemLoadAverage = operationSystemBean.getSystemLoadAverage();
    	
    	Double totalCpuLoad = processCpuLoad + systemCpuLoad;
    	
    	return Float.parseFloat(totalCpuLoad.toString());
    	
	}
	
	
	public boolean isMySQLRunning(String cmd, String patternStr) {
		return this.getIsByShell(cmd, patternStr);
	}
	
	public boolean isGhostRunning(String cmd, String patternStr) {
		return this.getIsByShell(cmd, patternStr);
	}
	
	public String getGhostVersion(String str) {
		return this.getStringByShell(str);
	}
	
	public int getGhostSockCount(File directory, String fileNamePattern) {
		return getFileCount(directory, fileNamePattern);
	}
	
	

	
	
	
	
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
