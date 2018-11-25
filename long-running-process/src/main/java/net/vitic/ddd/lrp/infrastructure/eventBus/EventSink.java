package net.vitic.ddd.lrp.infrastructure.eventBus;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventSink {
	String EVENT_INPUT = "eventInput";

	@Input(EVENT_INPUT)
	SubscribableChannel input();
}
