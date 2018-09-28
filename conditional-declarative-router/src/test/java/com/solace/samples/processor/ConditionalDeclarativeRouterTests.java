package com.solace.samples.processor;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solace.samples.bindings.ConditionalProcessor;

import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
public class ConditionalDeclarativeRouterTests {
	@Autowired
	protected ConditionalProcessor channels;

	@Autowired
	protected MessageCollector collector;

	@Test
	public void testAnOutput() {
		Message<Integer> msg  = MessageBuilder.withPayload(1).setHeader("type", "bogey").build();
		channels.someInput().send(msg);
		assertThat(collector.forChannel(channels.lowOutput()), receivesPayloadThat(containsString("1")));

	}
	
	@Test
	public void testAnotherOutput() {
		Message<Integer> msg  = MessageBuilder.withPayload(1).setHeader("type", "eagle").build();
		channels.someInput().send(msg);
		assertThat(collector.forChannel(channels.highOutput()), receivesPayloadThat(containsString("1")));

	}

}
