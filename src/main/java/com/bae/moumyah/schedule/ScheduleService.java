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

import com.google.gson.Gson;

@Service
public class ScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    
	@Value("${console.server_url}")
    private String postUrl;
	
	@Value("${console.client_id}")
    private int clientId;
	
	@Value("${console.cluster_name}")
    private String clusterName;
	
	
	
	public HostDTO getHostDTO() {
		
		HostDTO hostDTO = new HostDTO();
		
		hostDTO.setId(clientId);
		hostDTO.setClusterName(clusterName);
		hostDTO.setCpuPercentage((float)0.67);
		hostDTO.setFreeDiskPercentage((float)45.1);
		hostDTO.setTotalDiskSize(42949672960L);		
		hostDTO.setFreeDiskSize(22949672960L);
		hostDTO.setMysqlDataSize(12949672960L);
		hostDTO.setGhostVersion("1.0.48");
		hostDTO.setGhostSockCount(0);
		hostDTO.setGhostPostponeFile(false);
		hostDTO.setGhostRunning(false);
		hostDTO.setMysqlPid(true);
		hostDTO.setMysqlRunning(true);
		hostDTO.setMysqlSock(true);

		
		logger.debug(hostDTO.toString());
		
		return hostDTO;
	}
	
	public MySQLDTO getMySQLDTO() {
		
		MySQLDTO mysqlDTO = new MySQLDTO();
		
		mysqlDTO.setId(clientId);
		mysqlDTO.setReportHostName("test-d1");
		mysqlDTO.setMysqlVersion("5.6.34");
		mysqlDTO.setInnodbVersion("234.234");
		mysqlDTO.setReadOnly(false);
		mysqlDTO.setMasterActiveCount(0);
		mysqlDTO.setSlaveCount(3);
		mysqlDTO.setMasterHostName("N/A");
		
		
		logger.debug(mysqlDTO.toString());
		
		return mysqlDTO;
	}
	

	
    @Scheduled(fixedRateString = "${console.fetch_metrics}")
    public void sendHostDTO() {
    	
    	
    	try {
    		
    		String apiUrl = "/host/savehost";
    		HostDTO hostDTO = this.getHostDTO();
    		
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
    		
    		String apiUrl = "/mysql/savemysql";
    		MySQLDTO mysqlDTO = this.getMySQLDTO();
    		
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
