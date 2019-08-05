package com.bae.moumyah.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "console")
public class ConsoleComponent {
	
	private long fetchMetrics;
	
	private String serverUrl;

	public long getFetchMetrics() {
		return fetchMetrics;
	}

	public void setFetchMetrics(long fetchMetrics) {
		this.fetchMetrics = fetchMetrics;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	
}
