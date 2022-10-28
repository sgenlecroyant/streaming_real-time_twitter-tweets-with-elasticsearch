package com.sgenlecroyant.twitter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TwitterUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String extractTweetId(String tweetAsString) {
		JsonNode tweetTree;
		String tweetId = null;
		try {
			tweetTree = objectMapper.readTree(tweetAsString);
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
