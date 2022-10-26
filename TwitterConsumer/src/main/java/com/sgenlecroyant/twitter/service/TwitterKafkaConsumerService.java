package com.sgenlecroyant.twitter.service;

import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgenlecroyant.twitter.kafka.broker.TwitterKafkaConsumer;

public class TwitterKafkaConsumerService {

	private final Logger logger = LoggerFactory.getLogger(TwitterKafkaConsumerService.class);

	private TwitterKafkaConsumer twitterKafkaConsumer = new TwitterKafkaConsumer();

	public void consumeTweets() {
		Consumer<String, String> consumer = twitterKafkaConsumer.createTwitterKafkaConsumer();

		consumer.subscribe(Arrays.asList("twitter-tweets"));

		while (true) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));

			for (ConsumerRecord<String, String> record : consumerRecords) {
				this.logger.info("CONSUMED: KEY => {} : VALUE => {}",record.key(), record.value());
			}
		}

	}

}
