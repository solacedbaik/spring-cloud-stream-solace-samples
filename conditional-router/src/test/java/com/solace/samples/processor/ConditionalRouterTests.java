package com.solace.samples.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solace.samples.bindings.MyProcessor;

import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
public class ConditionalRouterTests {
	@Autowired
	protected MyProcessor channels;

	@Autowired
	protected MessageCollector collector;

	@Test
	public void testRoutingOutput() {
		channels.testInput().send(new GenericMessage<Integer>(1));
		assertThat(collector.forChannel(channels.anOutput()), receivesPayloadThat(containsString("1")));

	}
	
	@Test
	public void testRoutingAnotherOutput() {
		channels.testInput().send(new GenericMessage<Integer>(100));
		assertThat(collector.forChannel(channels.anotherOutput()), receivesPayloadThat(containsString("100")));

	}

}
