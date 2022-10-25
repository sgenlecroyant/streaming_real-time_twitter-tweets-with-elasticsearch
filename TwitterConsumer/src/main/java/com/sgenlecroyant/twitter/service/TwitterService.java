package com.sgenlecroyant.twitter.service;

public class TwitterService {

	private GreetingService greetingService;

	public String greetNow() {
		return this.greetingService.greet();
	}

}
