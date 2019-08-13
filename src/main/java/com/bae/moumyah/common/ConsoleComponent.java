package com.bae.moumyah.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "console")
public class ConsoleComponent {
	
	private long fetch_metrics;
	
	private String server_url;

	public long getFetchMetrics() {
		return fetch_metrics;
	}

	public void setFetchMetrics(long fetch_metrics) {
		this.fetch_metrics = fetch_metrics;
	}

	public String getServerUrl() {
		return server_url;
	}

	public void setServerUrl(String server_url) {
		this.server_url = server_url;
	}

	
}
