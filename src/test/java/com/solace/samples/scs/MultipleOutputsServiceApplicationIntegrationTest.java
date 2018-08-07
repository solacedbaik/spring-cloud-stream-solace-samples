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

import com.solace.samples.scs.processor.MyProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MultipleOutputsServiceApplication.class)
@DirtiesContext
@SpringBootTest
public class MultipleOutputsServiceApplicationIntegrationTest {

    @Autowired
    private MyProcessor pipe;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void whenSendMessage_thenResponseIsInAOutput() {
        whenSendMessage(1);
        thenPayloadInChannelIs(pipe.anOutput(), 1);
    }

    @Test
    public void whenSendMessage_thenResponseIsInAnotherOutput() {
        whenSendMessage(11);
        thenPayloadInChannelIs(pipe.anotherOutput(), 11);
    }

    private void whenSendMessage(Integer val) {
        pipe.myInput()
            .send(MessageBuilder.withPayload(val)
                .build());
    }

    private void thenPayloadInChannelIs(MessageChannel channel, Integer expectedValue) {
        Object payload = messageCollector.forChannel(channel)
            .poll()
            .getPayload();
        assertEquals(expectedValue, Integer.valueOf((String)payload));
    }
}
