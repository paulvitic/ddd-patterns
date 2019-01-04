package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventStore {

    private final EventLogPersistence persistence;
    private final EventStream eventStream;

    @EventListener
    public void logEvent(DomainEvent event) {

        stream(event);
        EventLog eventLog = new EventLog(
                event.type(),
                event.aggregate(),
                event.aggregateId(),
                event.occurredOn(),
                event.toString());

        eventLog = persistence.save(eventLog);
        log.info(eventLog.toString());
    }

    @Transactional(readOnly = true)
    public List<EventLog> eventsSince(String aggregate,
                                      String aggregateId,
                                      long lastSequence) {
        return persistence
                .findAllByAggregateAndAggregateIdAndSequenceGreaterThanOrderBySequenceAsc(
                        aggregate, aggregateId, lastSequence);
    }

    /**
     * Streams the event if it is not to be handled locally only
     * @param event is the domain event
     */
    private void stream(DomainEvent event) {
        if (!event.isLocal()) {
            eventStream.send(event);
        }
    }

    static Logger logger(){
        return log;
    }
}
