package com.sgenlecroyant.twitter;

import java.util.List;

import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;

public interface TwitterTermTracker {

	public StatusesFilterEndpoint trackTerms(StatusesFilterEndpoint statusesFilterEndpoint, List<String> terms);

}
