package com.bae.moumyah.schedule;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



import com.google.gson.Gson;

@Service
public class ScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    
    @Autowired
    ScheduleSystemComponent scheduleSystemComponent;
    
    @Autowired
    ScheduleRepository scheduleRepository;
    
    
	@Value("${console.server_url}")
    private String postUrl;
	
	@Value("${console.client_id}")
    private int clientId;
	
	@Value("${console.cluster_name}")
    private String clusterName;
	
	
	private String mysqlDirectory = "/mysql";
	private String tmpDirectory = "/tmp";
	private String ghostPostponeFlag = "/tmp/ghost.postpone.flag";
	private String mysqldPid = "/var/run/mysqld/mysqld.pid";
	private String mysqldSock = "/var/run/mysqld/mysqld.sock";


	
    @Scheduled(fixedRateString = "${console.fetch_metrics}")
    public void sendHostDTO() {
    	
    	
    	try {
    		
    		/*
    		 * Set server url to feed up the information
    		 */
    		String apiUrl = "/host/savehost";
    		
    		
    		HostDTO hostDTO = new  HostDTO(); 
    		hostDTO.setId(clientId);
    		hostDTO.setClusterName(clusterName);
    		
    		
    		File mysqlDirectory    = new File(this.mysqlDirectory);
    		File tempDirectory     = new File(this.tmpDirectory);
    		File ghostPostponeFlag = new File(this.ghostPostponeFlag);
    		File mysqldPid          = new File(this.mysqldPid);
    		File mysqldSock         = new File(this.mysqldSock);
    		
    		
    		float fVaule = 0L;
    		Long lVaule = 0L;
    		String str = null;
    		int cnt = 0;
    		boolean isFile = false;
    		boolean isProcess = false;
    		
    		/*
    		 * CPU
    		 */
    		fVaule = scheduleSystemComponent.getCpuPercentage();
    		hostDTO.setCpuPercentage( fVaule );
    		logger.debug("DEBUG: CPU : "+ fVaule);
    		
    		
    		/*
    		 * Total disk size
    		 */
    		lVaule = mysqlDirectory.getTotalSpace();
    		hostDTO.setTotalDiskSize(lVaule);
    		logger.debug("DEBUG: mysql total disk size : "+ lVaule);
    		
    		
    		/*
    		 * free size
    		 */
    		lVaule = mysqlDirectory.getUsableSpace();
    		hostDTO.setFreeDiskSize(lVaule);
    		logger.debug("DEBUG: free disk size : "+ lVaule);
    		
 
    		/*
    		 * data  size
    		 */
    		lVaule =  mysqlDirectory.getTotalSpace()- mysqlDirectory.getUsableSpace();
    		hostDTO.setMysqlDataSize(lVaule);	
    		logger.debug("DEBUG: mysql data size : "+ lVaule);
    		
    		/*
    		 * free space percentage
    		 */
    		fVaule = (float)mysqlDirectory.getUsableSpace() / (float)mysqlDirectory.getTotalSpace() * 100;
    		hostDTO.setFreeDiskPercentage(fVaule);
    		logger.debug("DEBUG: free space percentage : "+ fVaule);
    		
    		/*
    		 * gh-ost version
    		 */
    		str = scheduleSystemComponent.getGhostVersion("gh-ost --version");
    		hostDTO.setGhostVersion(str);
    		logger.debug("DEBUG: gh-ost --version : "+ str);
    		
    		/*
    		 * gh-ost socket count
    		 */
    		cnt = scheduleSystemComponent.getGhostSockCount(tempDirectory, "gh-ost.*.*.sock");
    		hostDTO.setGhostSockCount(cnt);
    		logger.debug("DEBUG: gh-ost socket count : "+ cnt);
    		
    		/*
    		 * gh-ost postphone flag file
    		 */
    		isFile = ghostPostponeFlag.exists();
    		hostDTO.setGhostPostponeFile(isFile);
    		logger.debug("DEBUG: gh-ost postphone flag : "+ isFile);
    		
    		/*
    		 * gh-ost process
    		 */
    		isProcess = scheduleSystemComponent.isGhostRunning("ps -ef", "gh-ost");
    		hostDTO.setGhostRunning(isProcess);
    		logger.debug("DEBUG: gh-ost process : "+ isProcess);
    		
    		
    		/*
    		 * gh-ost postphone flag file
    		 */
    		isFile = mysqldPid.exists();
    		hostDTO.setMysqlPid(isFile);
    		logger.debug("DEBUG: mysqld pid : "+ isFile);
    		
    		
    		/*
    		 * mysqld process
    		 */
    		isProcess = scheduleSystemComponent.isMySQLRunning("ps -ef", "mysqld");
    		hostDTO.setMysqlRunning(isProcess);
    		logger.debug("DEBUG: mysql process : "+ isProcess);
    		
    		/*
    		 * mysqld sock
    		 */
    		isFile = mysqldSock.exists();
    		hostDTO.setMysqlPid(isFile);
    		logger.debug("DEBUG: mysqld sock : "+ isFile);
    		
    		
    		
    		/*
    		 * Gson setup using url and HostDTO
    		 * Objec to Json
    		 * Send Json via post 
    		 */
    	    Gson         gson          = new Gson();
    	    HttpClient   httpClient    = HttpClientBuilder.create().build();
    	    HttpPost     post          = new HttpPost(postUrl+apiUrl);
    	    StringEntity postingString = new StringEntity(gson.toJson(hostDTO));//gson.tojson() converts your pojo to json
    	    
    	    post.setEntity(postingString);
    	    post.setHeader("Content-type", "application/json");
    	    HttpResponse  response = httpClient.execute(post);

    	    if(response.getStatusLine().getStatusCode() != 200) {
    	    	logger.debug("------------------------ ERROR -------------------");
    	    	logger.error(postUrl);
    	    	logger.error(response.getStatusLine().toString());
    	    	logger.error(gson.toJson(hostDTO));
    	    }else {
    	    	logger.debug("------------------------ DEBUG -------------------");
    	    	logger.debug(postUrl);
    	    	logger.debug(response.getStatusLine().toString());
    	    	logger.debug(gson.toJson(hostDTO));
    	    }
    	    
    	    
    		
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
 
    	
    }
    
    
    
    
    @Scheduled(fixedRateString = "${console.fetch_metrics}")
    public void sendMySQLDTO() {
    	
    	try {
    		
    		/*
    		 * Set server url to feed up the information
    		 */
    		String apiUrl = "/mysql/savemysql";
    		
    		
    		MySQLDTO mysqlDTO = new MySQLDTO();
    		mysqlDTO.setId(clientId);
    		mysqlDTO.setReportHostName(clusterName);
    		
    		
    		List<String> list = null;
    		Iterator<String> itr = null;
    		
    		List<Integer> listIntg = null;
    		Iterator<Integer> itrIntg = null;
    		
    		
    		/*
    		 * set innodb version
    		 */
    		list = scheduleRepository.findByVariableByName("innodb_version");
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: innodb_version: "+ str);
    			mysqlDTO.setInnodbVersion(str);
    		}
    		
    		
    		/*
    		 * set mysql version
    		 */
    		list = scheduleRepository.findByVariableByName("version");
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: version: "+ str);
    			mysqlDTO.setMysqlVersion(str);
    		}
    		
    		
    		/*
    		 * set master host name
    		 */
    		list = scheduleRepository.findMasterHostInSlaveStatus();
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: master host name: "+ str);
    			mysqlDTO.setMasterHostName(str);
    		}
    		
    		
    		/*
    		 * set master count
    		 */
    		listIntg = scheduleRepository.findMasterCount();
    		itrIntg = (Iterator<Integer>)listIntg.iterator();
    		while(itrIntg.hasNext()) {
    			Integer intg = (Integer)itrIntg.next();
    			logger.debug("DEBUG: active master count: "+ intg.toString());
    			mysqlDTO.setMasterActiveCount(intg.intValue());
    		}
    		
    		/*
    		 * set slave  count
    		 */
    		listIntg = scheduleRepository.findSlaveCount();
    		itrIntg = (Iterator<Integer>)listIntg.iterator();
    		while(itrIntg.hasNext()) {
    			Integer intg = (Integer)itrIntg.next();
    			logger.debug("DEBUG: slave count: "+ intg.toString());
    			mysqlDTO.setSlaveCount(intg.intValue());
    		}
    		
    		/*
    		 * set slave host name
    		 */
    		list = scheduleRepository.findSlaveHostName();
    		itr = (Iterator<String>)list.iterator();
    		StringBuilder slaveHostNames = new StringBuilder();
    		int cnt= 0;
    		while(itr.hasNext()) {
    			if(cnt != 0)slaveHostNames.append(",");
    			String str = (String)itr.next();
    			String[] arrOfStr = str.split(":", 2);
    			logger.debug("DEBUG: slave host name: "+ arrOfStr[0].toString());
    			slaveHostNames.append(arrOfStr[0].toString());
    			cnt++;
    		}
    		mysqlDTO.setSlaveHostName(slaveHostNames.toString());
    		logger.debug("DEBUG: slave host names: "+ slaveHostNames.toString());
    		
    		
    		/*
    		 * set slave IO running
    		 */
    		list = scheduleRepository.findSlaveIORuningInSlaveStatus();
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: slave IO running: "+ str.toString());
    		}
    		
    		/*
    		 * set slave SQL running
    		 */
    		list = scheduleRepository.findSlaveSQLRuningInSlaveStatus();
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: slave SQL running: "+ str.toString());
    		}
    		
    		/*
    		 * set slave last error number
    		 */
    		list = scheduleRepository.findLastErrnoInSlaveStatus();
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: slave last error number : "+ str.toString());
    		}
    		
    		/*
    		 * set slave last error number
    		 */
    		list = scheduleRepository.findLastErrorInSlaveStatus();
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: slave last error message: "+ str.toString());
    		}
    		
    		/*
    		 * set second behind master
    		 */
    		try {
    			list = scheduleRepository.findSecondBehindMasterInSlaveStatus();
        		itr = (Iterator<String>)list.iterator();
        		while(itr.hasNext()) {
        			String str = (String)itr.next();
        			logger.debug("DEBUG: slave second behind master: "+ str.toString());
        		}
    		}catch(Exception e) {
    			logger.debug("DEBUG: slave second behind master is NULL");
    		}
    		
    		
    		/*
    		 * set report host name
    		 */
    		list = scheduleRepository.findByVariableByName("report_host");
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: report host name: "+ str);
    			mysqlDTO.setReportHostName(str);
    		}
    		
    		
    		/*
    		 * set read only
    		 */
    		list = scheduleRepository.findByVariableByName("read_only");
    		itr = (Iterator<String>)list.iterator();
    		while(itr.hasNext()) {
    			String str = (String)itr.next();
    			logger.debug("DEBUG: read_only: "+ str);
    			
    			if(str.equals("OFF")) {
    				mysqlDTO.setReadOnly(false);
    			}else {
    				mysqlDTO.setReadOnly(true);
    			}
    			
    		}
    		
    	    		
 
    		
    		/*
    		 * Gson setup using url and mysqlDTO
    		 * Objec to Json
    		 * Send Json via post 
    		 */
    	    Gson         gson          = new Gson();
    	    HttpClient   httpClient    = HttpClientBuilder.create().build();
    	    HttpPost     post          = new HttpPost(postUrl+apiUrl);
    	    StringEntity postingString = new StringEntity(gson.toJson(mysqlDTO));//gson.tojson() converts your pojo to json
    	    
    	    post.setEntity(postingString);
    	    post.setHeader("Content-type", "application/json");
    	    HttpResponse  response = httpClient.execute(post);
    	    
    	    
    	    if(response.getStatusLine().getStatusCode() != 200) {
    	    	logger.debug("------------------------ ERROR -------------------");
    	    	logger.error(postUrl);
    	    	logger.error(response.getStatusLine().toString());
    	    	logger.error(gson.toJson(mysqlDTO));
    	    }else {
    	    	logger.debug("------------------------ DEBUG -------------------");
    	    	logger.debug(postUrl);
    	    	logger.debug(response.getStatusLine().toString());
    	    	logger.debug(gson.toJson(mysqlDTO));
    	    }
    	    
    	    
    		
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
