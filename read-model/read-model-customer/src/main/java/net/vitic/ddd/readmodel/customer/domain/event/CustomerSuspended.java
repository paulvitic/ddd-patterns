package net.vitic.ddd.readmodel.customer.domain.event;

import lombok.Value;
import net.vitic.ddd.domain.event.DomainEvent;

import java.util.Date;

@Value
public class CustomerSuspended implements DomainEvent {

    private final String aggregateId;
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;

    @Override
    public String type() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int version() {
        return 0;
    }

    @Override
    public String aggregateId() {
        return this.aggregateId;
    }

    @Override
    public Date occurredOn() {
        return new Date();
    }

    @Override
    public Long sequence() {
        return 0L;
    }
}
