package com.sgenlecroyant.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.sgenlecroyant.twitter.service.TwitterKafkaConsumerService;

@SpringBootApplication
@RestController
public class TwitterConsumer {

	public static void main(String[] args) {
		SpringApplication.run(TwitterConsumer.class, args);

		TwitterKafkaConsumerService twitterKafkaConsumerService = new TwitterKafkaConsumerService();
		twitterKafkaConsumerService.consumeTweets();
	}

}
