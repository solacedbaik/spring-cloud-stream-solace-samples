package com.solace.samples.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import com.solace.samples.bindings.MyProcessor;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class ConditionalRouter {

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
        return MessageBuilder.withPayload(val).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConditionalRouter.class, args);
    }
    
}
