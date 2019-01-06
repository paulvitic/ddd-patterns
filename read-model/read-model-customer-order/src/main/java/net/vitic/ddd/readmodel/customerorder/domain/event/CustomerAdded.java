package net.vitic.ddd.readmodel.customerorder.domain.event;


import lombok.Value;
import net.vitic.ddd.domain.event.DomainEvent;

import java.util.Date;

@Value
public class CustomerAdded implements DomainEvent {

    private String id;
    private String firstName;
    private String lastName;

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
        return this.getId();
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
