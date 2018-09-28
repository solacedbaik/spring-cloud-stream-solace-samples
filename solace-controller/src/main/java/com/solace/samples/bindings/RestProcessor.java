package com.solace.samples.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RestProcessor {
    String INPUT = "restInput";
    String OUTPUT = "restOutput";

    @Input
    SubscribableChannel restInput();

    @Output
    MessageChannel restOutput();
}
