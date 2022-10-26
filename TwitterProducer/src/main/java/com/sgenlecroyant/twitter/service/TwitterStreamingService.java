package com.sgenlecroyant.twitter.service;

import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenlecroyant.twitter.kafka.broker.twitter.producer.TwitterKafkaProducer;
import com.twitter.hbc.core.Client;

public class TwitterStreamingService implements TwitterStreamsRunner {

	private final static Logger LOGGER = LoggerFactory.getLogger(TwitterStreamingService.class);
	private static final String TWITTER_TWEET_TOPIC = "twitter-tweets";
	private Producer<String, String> kafkaProducer;
	private TwitterKafkaProducer twitterKafkaProducer = new TwitterKafkaProducer();
	private ObjectMapper objectMapper = new ObjectMapper();

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
					String key = this.extractTweetId(message);
					ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(
							TWITTER_TWEET_TOPIC, key, message);
					this.kafkaProducer.send(producerRecord, new Callback() {

						@Override
						public void onCompletion(RecordMetadata metadata, Exception exception) {
							if (exception == null) {
							} else {
								LOGGER.error("Error while sending records: {}", exception.getMessage());
							}
						}
					});
					message = null;
				} catch (InterruptedException e) {
					LOGGER.error("Encountered an error: " + e);
				}
			}

		}

	}

	public String extractTweetId(String tweetAsString) {
		JsonNode tweetTree;
		String tweetId = null;
		try {
			tweetTree = this.objectMapper.readTree(tweetAsString);
			tweetId = tweetTree.get("id").asText();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweetId;
	}

}
