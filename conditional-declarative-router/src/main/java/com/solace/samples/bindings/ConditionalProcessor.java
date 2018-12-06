package com.solace.samples.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ConditionalProcessor {
    String INPUT1 = "input1";
    String INPUT2 = "input2";

    @Input
    SubscribableChannel input1();

    @Input
    SubscribableChannel input2();
    
    @Output
    MessageChannel output1();

    @Output
    MessageChannel output2();
}
