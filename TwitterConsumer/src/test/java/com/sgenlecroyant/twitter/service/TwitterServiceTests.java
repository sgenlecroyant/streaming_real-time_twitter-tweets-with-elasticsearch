package com.sgenlecroyant.twitter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TwitterServiceTests {

	@Mock
	private GreetingService greetingService;
	
	@InjectMocks
	public TwitterService twitterService;

	@Test
	@DisplayName("SayHi Test")
	public void sayHi() {
		String value = "Hello People";
		when(this.greetingService.greet()).thenReturn(value);
		String greetNow = this.twitterService.greetNow();
		
		assertThat(value.equals(greetNow));
		verify(this.greetingService).greet();
	}

}
