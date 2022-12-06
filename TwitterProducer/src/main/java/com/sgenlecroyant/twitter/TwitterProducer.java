package com.sgenlecroyant.twitter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sgenlecroyant.twitter.client.TwitterClient;
import com.sgenlecroyant.twitter.client.TwitterClientAuth;
import com.sgenlecroyant.twitter.client.TwitterClientAuthService;
import com.sgenlecroyant.twitter.service.TwitterStreamingService;
import com.sgenlecroyant.twitter.service.TwitterStreamsRunner;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;

@SpringBootApplication
public class TwitterProducer implements CommandLineRunner {

	@Autowired
	private TwitterConfigService twitterConfigService;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TwitterProducer.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		BlockingQueue<String> messages = new ArrayBlockingQueue<>(1000);

		Hosts hosts = new HttpHosts(Constants.STREAM_HOST);

		String consumerKey = this.twitterConfigService.getConsumerkey();
		String consumerSecret = this.twitterConfigService.getConsumersecret();
		String token = this.twitterConfigService.getToken();
		String tokenSecret = this.twitterConfigService.getTokensecret();

		TwitterClientAuth twitterClientAuth = new TwitterClientAuthService();

		Authentication authentication = twitterClientAuth.authenticate(consumerKey, consumerSecret, token, tokenSecret);

		StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();
		TwitterTermTracker twitterTermTracker = new TwitterTermTrackerService();
		List<String> terms = Arrays.asList("bitcoin", "chelsea", "trump", "burundi");
		twitterTermTracker.trackTerms(statusesFilterEndpoint, terms);

		HosebirdMessageProcessor messageProcessor = new StringDelimitedProcessor(messages);
		Client client = TwitterClient.createClient(authentication, statusesFilterEndpoint, hosts, messageProcessor);
		client.connect();

		TwitterStreamsRunner streamsRunner = new TwitterStreamingService();

		streamsRunner.startStreamingRealTimeTwitterFeeds(client, messages);

	}

}
