package com.sgenlecroyant.twitter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@SpringBootApplication
public class TwitterProducer {
	private final static Logger LOGGER = LoggerFactory.getLogger(TwitterProducer.class);

	public static void main(String[] args) {
		SpringApplication.run(TwitterProducer.class, args);

		BlockingQueue<String> messages = new ArrayBlockingQueue<>(1000);

		Hosts hosts = new HttpHosts(Constants.STREAM_HOST);

		String consumerKey = "hYYGyQV241Gt7sb2rKLrNcBgy";
		String consumerSecret = "pso3ZO6N7TjgOOehax241QOIAvo4rnryuXWkVOPEacIXjzUvlV";
		String token = "990132492949704705-WiVkaj9zhtQDo98uq2NqccJt3HxrH64";
		String tokenSecret = "UDyAkL38mQLwhkUvDMyN7e996M6BRdY90rnoTHh1cBOoJ";

		Authentication authentication = new OAuth1(consumerKey, consumerSecret, token, tokenSecret);

		StatusesFilterEndpoint statusesFilterEndpoint = new StatusesFilterEndpoint();
		List<String> terms = Arrays.asList("bitcoin", "chelsea", "trump", "burundi");
		statusesFilterEndpoint.trackTerms(terms);

		Client client = new ClientBuilder().authentication(authentication).endpoint(statusesFilterEndpoint).hosts(hosts)
				.processor(new StringDelimitedProcessor(messages)).build();

		client.connect();
		String message = null;

		while (!client.isDone()) {
			if (message == null) {
				try {
					message = messages.take();
				} catch (InterruptedException e) {
					LOGGER.error("Encountered an error: " + e);
				}
			}

//			client.
		}

	}

}
