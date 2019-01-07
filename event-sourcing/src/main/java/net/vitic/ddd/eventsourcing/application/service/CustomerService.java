package net.vitic.ddd.eventsourcing.application.service;

import lombok.RequiredArgsConstructor;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.eventsourcing.domain.model.Customer;
import net.vitic.ddd.eventsourcing.infrastructure.eventStore.EventStore;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final EventStore eventStore;
    private final ApplicationEventPublisher publisher;

    public void createCustomer(CreateCustomerCmd command) {
        publisher.publishEvent(Customer.create(
                                            command.getFirstName(),
                                            command.getLastName(),
                                            command.getDateOfBirth()));
    }

    public void changeCustomerFirstName(ChangeCustomerFirstNameCmd command) {
        new Customer(this.pastEvents(command.getAgrregateId()))
            .updateFirstName(command.getName())
            .ifPresent(publisher::publishEvent);
    }


    private List<DomainEvent> pastEvents(String aggregateId) {
        return eventStore.aggregateEvents(Customer.class.getName(), aggregateId);
    }
}
