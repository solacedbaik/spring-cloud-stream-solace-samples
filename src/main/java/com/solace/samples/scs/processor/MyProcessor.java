package com.solace.samples.scs.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyProcessor {
    String INPUT = "testInput";
    String OUTPUT = "anotherOutput";

    @Input
    SubscribableChannel testInput();

    @Output("myOutput")
    MessageChannel anOutput();

    @Output
    MessageChannel anotherOutput();
}
