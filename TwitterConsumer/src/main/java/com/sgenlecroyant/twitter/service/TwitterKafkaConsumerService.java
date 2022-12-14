package com.sgenlecroyant.twitter.service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elasticsearch.client.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenlecroyant.twitter.elasticsearch.ElasticsearchConfig;
import com.sgenlecroyant.twitter.kafka.broker.TwitterKafkaConsumer;
import com.sgenlecroyant.twitter.model.Tweet;
import com.sgenlecroyant.twitter.util.TwitterUtils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;

public class TwitterKafkaConsumerService {

	private final Logger logger = LoggerFactory.getLogger(TwitterKafkaConsumerService.class);

	private TwitterKafkaConsumer twitterKafkaConsumer = new TwitterKafkaConsumer();

	private ElasticsearchClient elasticsearchClient = ElasticsearchConfig.getElasticsearchClient("localhost", 9200);

	public void consumeTweets() throws ElasticsearchException, IOException, InterruptedException {

		HealthResponse healthResponse = this.elasticsearchClient.cluster().health();
		System.out.println("Health Status: " + healthResponse.clusterName());
		Consumer<String, String> consumer = twitterKafkaConsumer.createTwitterKafkaConsumer();

		consumer.subscribe(Arrays.asList("twitter-tweets"));
		// TODO: CREATE A TWEET MODEL AND MAP THE TWEET STRING TO THIS MODEL, TO SEE IF
		// THIS CONTENT TYPE ERROR WILL BE FIXED
		Map<String, Tweet> tweetsBulkData = new HashMap<>();
		while (true) {
			BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
			ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));
			if (consumerRecords.count() > 0) {
				logger.info("Received: " + consumerRecords.count() + " recodrs!");

				for (ConsumerRecord<String, String> record : consumerRecords) {
					String tweet = record.value();
					String tweetId = TwitterUtils.extractTweetId(tweet);
					Tweet tweetInstance = this.readWithObjectMapper(tweet);
					tweetsBulkData.put(tweetId, tweetInstance);
//					this.logger.info("CONSUMED: KEY => {} : VALUE => {}", record.key(), record.value());
				}

				for (Map.Entry<String, Tweet> entry : tweetsBulkData.entrySet()) {

					// @formatter:off
					bulkRequestBuilder.operations((operation) -> operation
							.index((idx) -> idx.index("twitter-tweets")
									.id(entry.getKey())
									.document(entry.getValue())));
					logger.info(entry.getKey());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
				BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequestBuilder.build());
				consumer.commitSync();
				logger.info("Committed All Offsets | " + bulkResponse);
				System.out.println();
				tweetsBulkData.clear();
				bulkRequestBuilder = null;
				
				
			} else {
				logger.error("No Data Received");
			}
		}

	}
	
	public Tweet readWithObjectMapper(String tweetAsString) throws JsonMappingException, JsonProcessingException, InterruptedException {
		Tweet tweet = new Tweet();
		JsonNode jsonNode = new ObjectMapper().readTree(tweetAsString);
		String id = jsonNode.get("id").asText();
		String text = jsonNode.get("text").asText();
		tweet.setId(id);
		tweet.setText(text);
		return tweet;
	}

}
