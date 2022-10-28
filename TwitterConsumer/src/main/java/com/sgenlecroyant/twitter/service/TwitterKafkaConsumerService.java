package com.sgenlecroyant.twitter.service;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgenlecroyant.twitter.elasticsearch.ElasticsearchConfig;
import com.sgenlecroyant.twitter.kafka.broker.TwitterKafkaConsumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;

public class TwitterKafkaConsumerService {

	private final Logger logger = LoggerFactory.getLogger(TwitterKafkaConsumerService.class);

	private TwitterKafkaConsumer twitterKafkaConsumer = new TwitterKafkaConsumer();

	private ElasticsearchClient elasticsearchClient = ElasticsearchConfig.getElasticsearchClient("localhost", 9200);

	public void consumeTweets() throws ElasticsearchException, IOException, InterruptedException {

		HealthResponse healthResponse = this.elasticsearchClient.cluster().health();
		System.out.println("Health Status: " + healthResponse.clusterName());
		Consumer<String, String> consumer = twitterKafkaConsumer.createTwitterKafkaConsumer();

		consumer.subscribe(Arrays.asList("twitter-tweets"));

		while (true) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));

			for (ConsumerRecord<String, String> record : consumerRecords) {
				this.logger.info("CONSUMED: KEY => {} : VALUE => {}", record.key(), record.value());
			}
		}

	}

}
