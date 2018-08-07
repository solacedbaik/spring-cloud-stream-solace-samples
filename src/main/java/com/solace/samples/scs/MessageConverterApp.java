package com.solace.samples.scs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;

import com.solace.samples.scs.messages.TextPlainMessageConverter;
import com.solace.samples.scs.model.LogMessage;

@SpringBootApplication
@EnableBinding(Processor.class)
public class MessageConverterApp {

    @Bean
    public MessageConverter providesTextPlainMessageConverter() {
        return new TextPlainMessageConverter();
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public LogMessage enrichLogMessage(LogMessage log) {
        return new LogMessage(String.format("[1]: %s", log.getMessage()));
    }
}
