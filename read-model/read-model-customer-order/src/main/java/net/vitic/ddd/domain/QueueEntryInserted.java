package net.vitic.ddd.domain;

import net.vitic.ddd.domain.event.DomainEvent;

import java.util.Date;

public class QueueEntryInserted implements DomainEvent {

    @Override
    public String type() {
        return DomainEventType.valueOf(
            this.getClass().getSimpleName())
                              .name();
    }

    @Override
    public int version() {
        return 0;
    }

    @Override
    public Date occurredOn() {
        return null;
    }

    @Override
    public Long sequence() {
        return 0L;
    }

    @Override
    public String aggregateId() {
        return null;
    }
}
