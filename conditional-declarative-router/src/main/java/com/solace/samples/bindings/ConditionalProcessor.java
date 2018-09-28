package com.solace.samples.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ConditionalProcessor {
    String INPUT = "someInput";

    @Input
    SubscribableChannel someInput();

    @Output
    MessageChannel lowOutput();

    @Output
    MessageChannel highOutput();
}
