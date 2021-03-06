package net.vitic.ddd.infrastructure.eventBus;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventSource {
	String EVENT_OUTPUT = "eventOutput";

	@Output(EVENT_OUTPUT)
	MessageChannel output();
}
