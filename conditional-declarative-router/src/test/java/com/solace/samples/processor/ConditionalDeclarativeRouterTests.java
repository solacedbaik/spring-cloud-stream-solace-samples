package com.solace.samples.processor;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solace.samples.bindings.ConditionalProcessor;

import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
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
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testAnOutput() {
		Message<Integer> msg  = MessageBuilder.withPayload(1)
				.setHeader("type", "bogey")
				.setHeader("deliveryAttempt", 1)
				.build();
		channels.input1().send(msg);
		assertThat(collector.forChannel(channels.output2()), receivesPayloadThat(containsString("1")));

	}
	
	@Test
	public void testAnotherOutput() {
		Message<Integer> msg  = MessageBuilder.withPayload(1)
				.setHeader("type", "eagle")
				.setHeader("deliveryAttempt", 1)
				.build();
		channels.input2().send(msg);
		assertThat(collector.forChannel(channels.output1()), receivesPayloadThat(containsString("1")));
	}

	@Test
	public void testError() {
		exceptionRule.expect(MessagingException.class);
		exceptionRule.expectMessage("testException");
		
		channels.input1().send(MessageBuilder.withPayload(1).build());
	}
}
