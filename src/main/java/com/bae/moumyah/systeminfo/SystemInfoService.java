package com.bae.moumyah.systeminfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class SystemInfoService {
	
	@Autowired
    private Environment env;
	
	
	private static final Logger logger = LoggerFactory.getLogger(SystemInfoService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    
    
    private OperatingSystemMXBean operationSystemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    
    private File mysqlDirectory = new File("/mysql");
    
    private File tmpDirectory = new File("/tmp");
    
    
    private File ghostPostponeFlag = new File("/tmp/ghost.postpone.flag");
    private File mysqldPid = new File("/var/run/mysqld/mysqld.pid");
    private File mysqldSock = new File("/var/run/mysqld/mysqld.sock");
    
    
    final String cmd = "ps -ef";
    
    final String checkMysqld = "mysqld";
    
    final String checkGhost = "NetworkManager";
    
    final String checkGhostSock = "gh-ost.*.*.sock";
    
    
      
    
    
    public Double getCpuLoad() {
    	
    	CpuLoad cpuLoad = new CpuLoad();
    	
    	
    	Double processCpuLoad = operationSystemBean.getProcessCpuLoad() * 100;
    	Double systemCpuLoad  = operationSystemBean.getSystemCpuLoad() * 100;
    	Double systemLoadAverage = operationSystemBean.getSystemLoadAverage();
    	
    	Double totalCpuLoad = processCpuLoad + systemCpuLoad;
    	
    	cpuLoad.setProcessCpuLoad(processCpuLoad);
    	cpuLoad.setSystemCpuLoad(systemCpuLoad);
    	
    	
    	
    	
    	logger.info(cpuLoad.toString());
    	logger.info(totalCpuLoad.toString());
    	logger.info(systemLoadAverage.toString());
    	
    	logger.info(String.format("Total space: %.2f GB", (double)mysqlDirectory.getTotalSpace() /1073741824));
    	logger.info(String.format("Free space: %.2f GB", (double)mysqlDirectory.getFreeSpace() /1073741824));
    	logger.info(String.format("Usable space: %.2f GB", (double)mysqlDirectory.getUsableSpace() /1073741824));
    	
    	
    	
    	Pattern mysqldPattern = Pattern.compile(checkMysqld);
    	
    	
    	Pattern ghostPattern = Pattern.compile(checkGhost);
    	
    	boolean applicationIsOk = false;
    	
    	
    		
		Runtime run = Runtime.getRuntime();
		Process pr;
		
		try {
			pr = run.exec(cmd);
		
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line=buf.readLine())!=null) {
				
				
				Matcher mysqldMatcher = mysqldPattern.matcher(line);
				Matcher ghostMatcher = ghostPattern.matcher(line);
			    
			    if (mysqldMatcher.find() || ghostMatcher.find() ) {
			        logger.info(line);
			    }
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  
		if(ghostPostponeFlag.exists()) {
			logger.info("ghostPostponeFlag: YES");
		}else {
			logger.info("ghostPostponeFlag: NO");
		}
		
		
		
		Pattern ghostSockPattern = Pattern.compile(checkGhostSock);
		
		String children[] = tmpDirectory.list();
		for(int i = 0; i < children.length; i++) {
			
			
			Matcher ghostSockMatcher = ghostSockPattern.matcher(children[i]);
			
			if (ghostSockMatcher.find()) {
				logger.info(children[i]);
		    }
			
			
		}
		
    	
    	
        return null;
        
    }
    
  
}
