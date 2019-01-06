package net.vitic.ddd.readmodel.customerorder.domain.event;

import lombok.Value;
import net.vitic.ddd.domain.event.DomainEvent;

import java.util.Date;

@Value
public class CustomerRemoved implements DomainEvent {

    private String id;

    @Override
    public String type() {
        return this.getClass().getSimpleName();
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
        return getId();
    }

    @Override
    public String aggregate() {
        return null;
    }

    @Override
    public boolean isLocal() {
        return false;
    }
}
