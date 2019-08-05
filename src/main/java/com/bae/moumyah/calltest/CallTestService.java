package com.bae.moumyah.calltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class CallTestService {

	private static final Logger logger = LoggerFactory.getLogger(CallTestService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String call2() {
    	
    	logger.info("call2");
    	
    	return "call2";
    }
    
    
    @Scheduled(fixedRateString = "${console.fetchMetrics}")
    public void sendCall() {
    	
    	String returnValue = "";
    	
    	HttpURLConnection conn = null;
    	
    	try {

    		URL url = new URL("http://localhost:8080/calltest/call2");
    		conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Accept", "application/json");

    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));

    		
    		logger.info("Output from Server .... \n");
    		String output;
    		while ((output = br.readLine()) != null) {
    			logger.info(output);
    			if ( output != null ) returnValue = returnValue + output;
    		}
    		
    		
    		conn.disconnect();

    	  } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  } finally {

    		conn.disconnect();
    	  }
    	
    	logger.info(returnValue);
 
    	
    }
    
}
