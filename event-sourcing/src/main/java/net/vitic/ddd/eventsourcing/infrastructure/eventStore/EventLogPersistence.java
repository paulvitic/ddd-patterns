package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventLogPersistence extends CrudRepository<EventLog, Integer> {

    List<EventLog> findAllByAggregateAndAggregateIdAndSequenceGreaterThanOrderBySequenceAsc(
        String aggregate, String aggregateId, Long startSequence);

    List<EventLog> findAllByAggregateAndAggregateIdOrderBySequenceAsc(String aggregate,
                                                                      String aggregateId);
}
