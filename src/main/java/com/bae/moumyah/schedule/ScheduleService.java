package com.bae.moumyah.schedule;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;

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

import com.bae.moumyah.common.HostComponent;
import com.bae.moumyah.common.HostComponentDTO;
import com.google.gson.Gson;

@Service
public class ScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    
    @Autowired
    HostComponent hostComponent;
    
	@Value("${console.server_url}")
    private String postUrl;
	
	@Value("${console.client_id}")
    private int clientId;
	
	@Value("${console.cluster_name}")
    private String clusterName;
	
	
	private final String mysqlDirectory = "/mysql";
	private final String tmpDirectory = "/tmp";
	private final String ghostPostponeFlag = "/tmp/ghost.postpone.flag";
	private final String mysqldPid = "/var/run/mysqld/mysqld.pid";
	private final String mysqldSock = "/var/run/mysqld/mysqld.sock";
	private final String cmd = "ps -ef";
	private final String checkMysqld = "mysqld";
	private final String checkGhost = "gh-ost";
	private final String checkGhostSock = "gh-ost.*.*.sock";

	
	
    @Scheduled(fixedRateString = "${console.fetch_metrics}")
    public void sendHostDTO() {
    	
    	
    	try {
    		
    		/*
    		 * Set server url to feed up the information
    		 */
    		String apiUrl = "/host/savehost";
    		
    		/*
    		 * Set HostDTO information from OS ( Using HostComponent ) 
    		 */
    		HostComponentDTO hostComponentDTO = new HostComponentDTO();
    		
    		hostComponentDTO.setMysqlDirectory(mysqlDirectory);
    		hostComponentDTO.setTmpDirectory(tmpDirectory);
    		hostComponentDTO.setGhostPostponeFlag(ghostPostponeFlag);
    		hostComponentDTO.setMysqldPid(mysqldPid);
    		hostComponentDTO.setMysqldSock(mysqldSock);
    		hostComponentDTO.setCmd(cmd);
    		hostComponentDTO.setCheckMysqld(checkMysqld);
    		hostComponentDTO.setCheckGhost(checkGhost);
    		hostComponentDTO.setCheckGhostSock(checkGhostSock);
    		
    		
    		HostDTO hostDTO = hostComponent.getHostDTO(hostComponentDTO);
    		hostDTO.setId(clientId);
    		hostDTO.setClusterName(clusterName);
    		
    		
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
    	    	logger.error(gson.toJson(null));
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
    		// MySQLDTO mysqlDTO = this.getMySQLDTO();
    		
    		
    		
    		MySQLDTO mysqlDTO = hostComponent.getMySQLDTO();
    		mysqlDTO.setId(clientId);
    		mysqlDTO.setReportHostName("test-d1");
    		
    		
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
    	    	logger.error(gson.toJson(null));
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
