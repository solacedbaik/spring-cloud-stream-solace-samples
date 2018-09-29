package com.solace.samples.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solace.samples.bindings.RestProcessor;

import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
public class SolaceControllerTests {
	@Autowired
	protected RestProcessor channels;

	@Autowired
	protected MessageCollector collector;

	private static final String TEST_RESULT = "{\"id\":1,\"content\":\"Hello, Principal Skinner!\"}";

	@Test
	public void testGreeting() {
		channels.restInput().send(new GenericMessage<String>("Principal Skinner"));
		assertThat(collector.forChannel(channels.restOutput()), receivesPayloadThat(containsString(TEST_RESULT)));

	}
	
}
