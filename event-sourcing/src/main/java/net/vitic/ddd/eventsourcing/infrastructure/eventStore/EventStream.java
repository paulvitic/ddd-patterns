package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import lombok.RequiredArgsConstructor;
import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventStream {

    private final MessageChannel domainEventStream;

    void send(DomainEvent event) {
        domainEventStream.send(MessageBuilder.withPayload(event).build());
    }
}
