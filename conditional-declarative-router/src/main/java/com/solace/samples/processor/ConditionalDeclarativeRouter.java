package com.solace.samples.processor;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;

import com.solace.samples.bindings.ConditionalProcessor;

@SpringBootApplication
@EnableBinding(ConditionalProcessor.class)
public class ConditionalDeclarativeRouter {
    private static final Logger log = LoggerFactory.getLogger(ConditionalDeclarativeRouter.class);
    
    @Autowired
    private ConditionalProcessor processor;

    @StreamListener(target = ConditionalProcessor.INPUT1, condition = "headers['type']=='bogey'")
    public void routeValuesToAnOutput(Integer val) {
        processor.output2().send(message(val));
    }

    @StreamListener(target = ConditionalProcessor.INPUT2, condition = "headers['type']=='eagle'")
    public void routeValuesToAnotherOutput(Integer val, @Header long timestamp, @Header int deliveryAttempt) {
    	log.info("[(" + deliveryAttempt + ") " + new Timestamp(timestamp).toString() + "]: " + val);
    	
        processor.output1().send(message(val));
    }
    
    // App-level error handling - takes precedence over global error handler
    @ServiceActivator(inputChannel = ConditionalProcessor.INPUT1 + ".group.errors")
    public void appError(Message<?> message) {
    	System.out.println("Handling appERROR: " + message);
    }
    
    // Global error handler - all stream-specific error channels bridged to this channel    
    @StreamListener("errorChannel")
    public void globalError(Message<?> message) {
    	System.out.println("Handling globalERROR: " + message);
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConditionalDeclarativeRouter.class, args);
    }

}
