package net.vitic.ddd.domain.model;

import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private final transient List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent event) {
        Assert.notNull(event, "Domain event must not be null!");
        this.domainEvents.add(event);
    }

    //abstract protected void mutate(List<DomainEvent> events);

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    public List<DomainEvent> getDomainEvents() {
        return this.domainEvents;
    }
}
