package com.bae.moumyah.getosinfo;

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
public class HostService {
	
	private static final Logger logger = LoggerFactory.getLogger(HostService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    
	@Value("${console.serverUrl}")
    private String postUrl;
	
	@Value("${console.clientId}")
    private int clientId;
	
	
	
	public HostDTO getHostDTO() {
		
		HostDTO hostDTO = new HostDTO();
		
		hostDTO.setId(clientId);
		hostDTO.setCpuPercentage((float)0.67);
		
		logger.debug(hostDTO.toString());
		
		return hostDTO;
	}
	
	

	
    @Scheduled(fixedRateString = "${console.fetchMetrics}")
    public void sendHostDTO() {
    	
    	
    	try {
    		
    		HostDTO hostDTO = this.getHostDTO();
    		
    	    Gson         gson          = new Gson();
    	    HttpClient   httpClient    = HttpClientBuilder.create().build();
    	    HttpPost     post          = new HttpPost(postUrl);
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
    
    @Scheduled(fixedRateString = "${console.fetchMetrics}")
    public void sendDataDTO() {
    	
    	// Here? Really?
    	logger.debug("------------------------ MySQL DTATA -------------------");
    	
    }
	
}
