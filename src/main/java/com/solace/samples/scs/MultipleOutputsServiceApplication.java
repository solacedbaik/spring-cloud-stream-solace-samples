package com.solace.samples.scs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;

import org.springframework.messaging.support.MessageBuilder;

import com.solace.samples.scs.processor.MyProcessor;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class MultipleOutputsServiceApplication {

    @Autowired
    private MyProcessor processor;
    
    @StreamListener(MyProcessor.INPUT)
    public void routeValues(Integer val) {
    	if (val < 10) {
    		processor.anOutput().send(message(val));
    	} else {
            processor.anotherOutput().send(message(val));    		
    	}
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val)
            .build();
    }
}
