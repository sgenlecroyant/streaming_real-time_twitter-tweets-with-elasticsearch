package com.sgenlecroyant.twitter.service;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.hbc.core.Client;

public class TwitterStreamingService implements TwitterStreamsRunner {

	private final static Logger LOGGER = LoggerFactory.getLogger(TwitterStreamingService.class);

	@Override
	public void startStreamingRealTimeTwitterFeeds(Client client, BlockingQueue<String> messages) {
		String message = null;
		int count = 0;
		while (!client.isDone()) {
			if (message == null) {
				try {
					message = messages.take();
					LOGGER.info(++count + ":" + message);
					message = null;
				} catch (InterruptedException e) {
					LOGGER.error("Encountered an error: " + e);
				}
			}

		}

	}

}
