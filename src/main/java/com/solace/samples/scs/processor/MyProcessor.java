package com.solace.samples.scs.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyProcessor {
    String INPUT = "myInput";
    String OUTPUT = "anotherOutput";

    @Input
    SubscribableChannel myInput();

    @Output("myOutput")
    MessageChannel anOutput();

    @Output
    MessageChannel anotherOutput();
}
