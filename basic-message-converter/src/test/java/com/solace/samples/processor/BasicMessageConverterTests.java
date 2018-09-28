package com.solace.samples.processor;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest
public class BasicMessageConverterTests {
	@Autowired
	protected Processor channels;

	@Autowired
	protected MessageCollector collector;

	private static final String TEST_RESULT = "[1]: test";

	@Test
	public void testConversion() {
		channels.input().send(new GenericMessage<String>("test"));
		assertThat(collector.forChannel(channels.output()), receivesPayloadThat(containsString(TEST_RESULT)));

	}
	
}
