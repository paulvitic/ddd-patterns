package net.vitic.ddd.readmodel.customerorder.port.adapter.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventSink {
	String EVENT_INPUT = "eventInput";

	@Input(EVENT_INPUT)
	SubscribableChannel input();
}
