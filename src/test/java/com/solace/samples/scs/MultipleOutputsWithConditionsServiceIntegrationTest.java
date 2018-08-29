package com.solace.samples.scs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solace.samples.scs.processor.ConditionalProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MultipleOutputsWithConditionsServiceApplication.class)
@DirtiesContext
@SpringBootTest
public class MultipleOutputsWithConditionsServiceIntegrationTest {

    @Autowired
    private ConditionalProcessor pipe;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void whenSendMessage_thenResponseIsInAOutput() {
        whenSendMessageWithHeader(55, "bogey");
        thenPayloadInChannelIs(pipe.lowOutput(), 55);
    }

    @Test
    public void whenSendMessage_thenResponseIsInAnotherOutput() {
        whenSendMessageWithHeader(11, "eagle");
        thenPayloadInChannelIs(pipe.highOutput(), 11);
    }

    private void whenSendMessageWithHeader(Integer val, String header) {
        pipe.someInput().send(MessageBuilder.withPayload(val).setHeader("type", header).build());
    }

    private void thenPayloadInChannelIs(MessageChannel channel, Integer expectedValue) {
        Object payload = messageCollector.forChannel(channel)
            .poll()
            .getPayload();
        assertEquals(expectedValue, Integer.valueOf((String)payload));
    }
}
