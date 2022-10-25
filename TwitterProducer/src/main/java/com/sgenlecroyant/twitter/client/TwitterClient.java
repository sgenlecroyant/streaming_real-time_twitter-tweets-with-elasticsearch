package com.sgenlecroyant.twitter.client;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;

public interface TwitterClient {

	public static Client createClient(Authentication authentication, StatusesFilterEndpoint statusesFilterEndpoint,
			Hosts hosts, HosebirdMessageProcessor hosebirdMessageProcessor) {
		return new ClientBuilder().authentication(authentication).endpoint(statusesFilterEndpoint).hosts(hosts)
				.processor(hosebirdMessageProcessor).build();
	}

}
