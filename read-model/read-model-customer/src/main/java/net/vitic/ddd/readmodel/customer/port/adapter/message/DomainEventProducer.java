package net.vitic.ddd.readmodel.customer.port.adapter.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.infrastructure.ApplicationEventConsumer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableBinding({EventSource.class})
public class DomainEventProducer implements Consumer<DomainEvent> {

    public final static String MSG_HEADER_EVENT_ID = "eventId";
    public final static String MSG_HEADER_EVENT_TYPE = "eventType";

    private final ApplicationEventConsumer applicationEventConsumer;
    private final EventSource eventSource;

    @Override
    public void accept(final DomainEvent event) {
        eventSource.output().send(MessageBuilder
                                      .withPayload(event)
                                      .setHeader(MSG_HEADER_EVENT_TYPE,
                                                 event.getClass().getName() + ":"
                                                 + event.version())
                                      .setHeader(MSG_HEADER_EVENT_ID, event.aggregateId())
                                      .build());
    }

    @PostConstruct
    void init(){
        //create flux from spring application event consumer and subscribe to it
        Flux.create(applicationEventConsumer)
            .subscribe(this);
    }
}
