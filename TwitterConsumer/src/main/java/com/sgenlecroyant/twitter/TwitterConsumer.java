package com.sgenlecroyant.twitter;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.sgenlecroyant.twitter.service.TwitterKafkaConsumerService;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@SpringBootApplication
@RestController
public class TwitterConsumer {

	public static void main(String[] args) throws ElasticsearchException, IOException, InterruptedException {
		SpringApplication.run(TwitterConsumer.class, args);

		TwitterKafkaConsumerService twitterKafkaConsumerService = new TwitterKafkaConsumerService();
		twitterKafkaConsumerService.consumeTweets();
	}

}
