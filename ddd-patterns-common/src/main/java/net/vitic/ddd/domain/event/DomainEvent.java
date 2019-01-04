package net.vitic.ddd.domain.event;

import java.util.Date;

public interface DomainEvent {

    String type();

    int version();

    String aggregate();

    String aggregateId();

    Date occurredOn();

    Long sequence();

    boolean isLocal();
}
