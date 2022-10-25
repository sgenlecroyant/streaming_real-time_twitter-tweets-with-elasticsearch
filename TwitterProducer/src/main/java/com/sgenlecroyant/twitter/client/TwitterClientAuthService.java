package com.sgenlecroyant.twitter.client;

import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterClientAuthService implements TwitterClientAuth{

	@Override
	public Authentication authenticate(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		return new OAuth1(consumerKey, consumerSecret, token, tokenSecret);
	}

}
