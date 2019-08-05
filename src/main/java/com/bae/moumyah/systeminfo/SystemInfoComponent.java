package com.bae.moumyah.systeminfo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "console")
public class SystemInfoComponent {
	
	private long fetchMetrics;

	public long getFetchMetrics() {
		return fetchMetrics;
	}

	public void setFetchMetrics(long fetchMetrics) {
		this.fetchMetrics = fetchMetrics;
	}
	
	
	
}
