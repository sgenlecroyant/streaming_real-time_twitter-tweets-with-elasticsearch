package com.sgenlecroyant.twitter.service;

import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgenlecroyant.twitter.kafka.broker.twitter.producer.TwitterKafkaProducer;
import com.twitter.hbc.core.Client;

public class TwitterStreamingService implements TwitterStreamsRunner {

	private final static Logger LOGGER = LoggerFactory.getLogger(TwitterStreamingService.class);
	private static final String TWITTER_TWEET_TOPIC = "twitter-tweets";
	private Producer<String, String> kafkaProducer;
	private TwitterKafkaProducer twitterKafkaProducer = new TwitterKafkaProducer();

	@Override
	public void startStreamingRealTimeTwitterFeeds(Client client, BlockingQueue<String> messages) {
		this.kafkaProducer = this.twitterKafkaProducer.createProducer();

		String message = null;
		int count = 0;
		while (!client.isDone()) {
			if (message == null) {
				try {
					message = messages.take();
					LOGGER.info(++count + ":" + message);
					ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(
							TWITTER_TWEET_TOPIC, message);
					this.kafkaProducer.send(producerRecord);
					message = null;
				} catch (InterruptedException e) {
					LOGGER.error("Encountered an error: " + e);
				}
			}

		}

	}

}
