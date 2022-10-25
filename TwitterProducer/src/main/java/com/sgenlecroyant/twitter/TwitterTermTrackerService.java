package com.sgenlecroyant.twitter;

import java.util.List;

import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;

public class TwitterTermTrackerService implements TwitterTermTracker{

	@Override
	public StatusesFilterEndpoint trackTerms(StatusesFilterEndpoint statusesFilterEndpoint, List<String> terms) {
		statusesFilterEndpoint.trackTerms(terms);
		return statusesFilterEndpoint;
	}
	

}
