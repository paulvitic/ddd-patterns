package net.vitic.ddd.infrastructure.eventBus;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.events.DomainEvent;
import net.vitic.ddd.domain.events.PhoneNumberProcessEvent;
import net.vitic.ddd.domain.service.PhoneNumberProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;

@Component
@Slf4j
@EnableBinding({EventSink.class, EventSource.class})
public class DomainEventHandler implements Consumer<DomainEvent>{

    private final Map<String, Set<PhoneNumberProcessor>> processorsMap;
    private final ApplicationEventPublisher localEventPublisher;
    private final LocalEventConsumer localEventConsumer;
    private final EventSource eventSource;
    private final List<PhoneNumberProcessor> processors;


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public DomainEventHandler(ApplicationEventPublisher applicationEventPublisher,
                              LocalEventConsumer localEventConsumer,
                              EventSource eventSource,
                              List<PhoneNumberProcessor> processors) {
        this.processorsMap = new HashMap<>();
        this.localEventPublisher = applicationEventPublisher;
        this.localEventConsumer = localEventConsumer;
        this.eventSource = eventSource;

        this.processors = processors;
    }


	@StreamListener(EventSink.EVENT_INPUT)
	public void process(Flux<PhoneNumberProcessEvent> inbound) {
        //noinspection unchecked, OptionalGetWithoutIsPresent
        inbound
            .flatMap(event -> Flux.fromIterable(processorsMap.get(event.type()))
                                        .map(processor -> processor.process(event)))
            .filter(Optional::isPresent)
            .doOnNext(event -> this.publish((DomainEvent) event.get()))
            .subscribe();
	}


    @Override
    public void accept(final DomainEvent event) {
        //noinspection unchecked, OptionalGetWithoutIsPresent
        Flux.fromIterable(processorsMap.get(event.type()))
            .map(processor -> processor.process(event))
            .filter(Optional::isPresent)
            .doOnNext(result -> this.publish((DomainEvent) result.get()))
            .doOnError(this::handleError)
            .subscribe();
    }


    private void handleError(Throwable throwable) {
        log.error("Error while consuming application event flux: {}", throwable.getMessage(), throwable);
    }


    private void publish(DomainEvent event){
        if (event.isLocal()) {
            localEventPublisher.publishEvent(event);
        } else {
            eventSource.output().send(MessageBuilder
                                          .withPayload(event)
                                          .setHeader("type", event.getClass().getName())
                                          .setHeader("messageId", event.processId())
                                          .build());
        }
    }


    @PostConstruct
    void init(){
        // populate processors map
        //noinspection unchecked
        processors.forEach(processor -> processor.handles().forEach(type -> {
            processorsMap.computeIfAbsent((String) type, k -> new HashSet<>());
            processorsMap.get(type).add(processor);
        }));

        // create flux from spring application event consumer and subscribe to it
        Flux.create(localEventConsumer)
            .subscribe(this);
    }
}
