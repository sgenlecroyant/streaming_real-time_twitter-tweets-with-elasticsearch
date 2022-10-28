package com.sgenlecroyant.twitter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TwitterUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterUtils.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String extractTweetId(String tweetAsString) {
		JsonNode tweetTree;
		String tweetId = null;
		try {
			tweetTree = objectMapper.readTree(tweetAsString);
			tweetId = tweetTree.get("id").asText();
		} catch (JsonMappingException e) {
			LOGGER.error("Bad Data: Error Mapping => {}", e.getMessage());
		} catch (JsonProcessingException e) {
			LOGGER.error("Bad Data: Error Processing JSON => {}", e.getMessage());
		}
		return tweetId;
	}

}
