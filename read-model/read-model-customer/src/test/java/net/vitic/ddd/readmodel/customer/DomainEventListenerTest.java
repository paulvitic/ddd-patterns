package net.vitic.ddd.readmodel.customer;

import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

//@TestComponent
public class DomainEventListenerTest {

    private List<DomainEvent> domainEvents = new ArrayList<>();

    @EventListener
    public void handle(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents(){
        return this.domainEvents;
    }
}
