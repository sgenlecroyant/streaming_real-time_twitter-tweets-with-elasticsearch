package com.sgenlecroyant.twitter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
public class TwitterProducer {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TwitterProducer.class, args);

		BlockingQueue<String> messages = new ArrayBlockingQueue<>(1000);

		Hosts hosts = new HttpHosts(Constants.STREAM_HOST);

		String consumerKey = "QkVZbzd1tj5yrlWw5nDBMnWAC";
		String consumerSecret = "zNr5E8Y76gcq2eiWlYlrot0rfbIef3vhlyiAHc7VjWMu8kbspc";
		String token = "990132492949704705-O3hXe4BfUELS33CBpuerKhOjLunp5Yr";
		String tokenSecret = "9znP4Au7uJv4oGGMLaUXmGDwI8oArs9sEU5y4Ij3uWuvV";

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
