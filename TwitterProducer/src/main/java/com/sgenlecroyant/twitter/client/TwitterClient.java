package com.sgenlecroyant.twitter.client;

import com.twitter.hbc.core.Client;

public interface TwitterClient {

	public void connect();
	public Client createClient();

}
