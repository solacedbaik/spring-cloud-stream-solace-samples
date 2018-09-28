package com.solace.samples.processor;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solace.samples.domain.Greeting;
import com.solace.samples.bindings.RestProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

@RestController
@SpringBootApplication
@EnableBinding(RestProcessor.class)
public class SolaceController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RestProcessor processor;

    @StreamListener(RestProcessor.INPUT)
    @SendTo(RestProcessor.OUTPUT)
    public Greeting doGreeting(String name) {
    	Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
    	return greeting;
    }

    @RequestMapping("/greeting")
    public Greeting restGreeting(@RequestParam(value="name", defaultValue="World") String name) {
    	Greeting greeting = doGreeting(name);
    	processor.restOutput().send(message(greeting));    	
    	return greeting;
    }
    
    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SolaceController.class, args);
    }

}
