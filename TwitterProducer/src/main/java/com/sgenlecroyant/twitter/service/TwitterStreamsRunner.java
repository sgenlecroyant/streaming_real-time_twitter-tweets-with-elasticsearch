package com.sgenlecroyant.twitter.service;

import java.util.concurrent.BlockingQueue;

import com.twitter.hbc.core.Client;

public interface TwitterStreamsRunner {

	public void startStreamingRealTimeTwitterFeeds(Client client, BlockingQueue<String> messages);

}
