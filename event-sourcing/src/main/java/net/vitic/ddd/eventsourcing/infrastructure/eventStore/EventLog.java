package net.vitic.ddd.eventsourcing.infrastructure.eventStore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.vitic.ddd.domain.event.DomainEvent;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "event_log")
@ToString
@NoArgsConstructor
public class EventLog {

    @Id
    @Column(name = "sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventLogSequence")
    @SequenceGenerator(name = "eventLogSequence", sequenceName = "public.event_seq", allocationSize=1, schema = "public")
    private Long sequence;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "event")
    private String type;

    @Column(name = "aggregate")
    private String aggregate;

    @Column(name = "aggregate_id")
    private String aggregateId;

    @Column(name = "data", length = 2048)
    private String data;

    EventLog(String type,
             String aggregate,
             String aggregateId,
             Date date,
             String data) {
        this.type = type;
        this.aggregate = aggregate;
        this.aggregateId = aggregateId;
        this.timestamp = date;
        this.data = data;
    }

    Long getSequence() {
        return sequence;
    }

    Optional<DomainEvent> event()  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.enable(JsonParser.Feature.IGNORE_UNDEFINED);
        mapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);

        try {
            return Optional.of(mapper.readValue(data, DomainEvent.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
