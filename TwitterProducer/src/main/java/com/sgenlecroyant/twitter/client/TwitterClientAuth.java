package com.sgenlecroyant.twitter.client;

import com.twitter.hbc.httpclient.auth.Authentication;

public interface TwitterClientAuth {

	public Authentication authenticate(String consumerKey, String consumerSecret, String token, String tokenSecret);
}
