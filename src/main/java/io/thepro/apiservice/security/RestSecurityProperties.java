package io.thepro.apiservice.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security")
public class RestSecurityProperties {

	List<String> alloweddomains;
	List<String> allowedheaders;
	List<String> allowedmethods;
	List<String> allowedpublicapis;

	public List<String> getAlloweddomains() {
		return alloweddomains;
	}

	public void setAlloweddomains(List<String> alloweddomains) {
		this.alloweddomains = alloweddomains;
	}

	public List<String> getAllowedheaders() {
		return allowedheaders;
	}

	public void setAllowedheaders(List<String> allowedheaders) {
		this.allowedheaders = allowedheaders;
	}

	public List<String> getAllowedmethods() {
		return allowedmethods;
	}

	public void setAllowedmethods(List<String> allowedmethods) {
		this.allowedmethods = allowedmethods;
	}

	public List<String> getAllowedpublicapis() {
		return allowedpublicapis;
	}

	public void setAllowedpublicapis(List<String> allowedpublicapis) {
		this.allowedpublicapis = allowedpublicapis;
	}
}
