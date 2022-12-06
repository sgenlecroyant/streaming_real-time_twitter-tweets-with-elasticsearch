package com.sgenlecroyant.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twitter")
public class TwitterConfigService {
	
	@Value("${twitter.consumerkey}")
	private String consumerkey;
	@Value("${twitter.consumersecret}")
	private String consumersecret;
	@Value("${twitter.token}")
	private String token;
	@Value("${twitter.tokensecret}")
	private String tokensecret;

	public String getConsumerkey() {
		return consumerkey;
	}

	public void setConsumerkey(String consumerkey) {
		this.consumerkey = consumerkey;
	}

	public String getConsumersecret() {
		return consumersecret;
	}

	public void setConsumersecret(String consumersecret) {
		this.consumersecret = consumersecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokensecret() {
		return tokensecret;
	}

	public void setTokensecret(String tokensecret) {
		this.tokensecret = tokensecret;
	}


}
