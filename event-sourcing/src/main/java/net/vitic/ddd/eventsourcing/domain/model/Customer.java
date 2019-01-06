package net.vitic.ddd.eventsourcing.domain.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.eventsourcing.domain.EventSourcedAggregate;
import net.vitic.ddd.eventsourcing.domain.event.CustomerCreated;
import net.vitic.ddd.eventsourcing.domain.event.CustomerFirstNameUpdated;
import net.vitic.ddd.util.EntityIdGenerator;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Customer extends EventSourcedAggregate {

    public enum Status {ACTIVE, SUSPENDED, DELETED}

    private String id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private Status status;

    public Customer(List<DomainEvent> events) {
        this.reConstruct(events);
    }

    @Builder
    private Customer(@NonNull String firstName,
                     @NonNull String lastName,
                     @NonNull Date dateOfBirth) {
        registerEvent(
            createCustomerInternal(
                EntityIdGenerator.generate(),
                firstName,
                lastName,
                dateOfBirth));
    }

    public static Customer create(String firstName,
                                  String lastName,
                                  Date dateOfBirth) {
        return Customer.builder()
                       .firstName(firstName)
                       .lastName(lastName)
                       .dateOfBirth(dateOfBirth)
                       .build();
    }

    public void updateFirstName(String firstName){
        this.updateFirstNameInternal(firstName)
            .ifPresent(this::registerEvent);
    }

    public String id() {
        return this.id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Date dateOfBirth() {
        return dateOfBirth;
    }

    public Status status() {
        return status;
    }

    @Override
    protected void mutate(DomainEvent event) {
        select()
            .when(CustomerCreated.class, this::customerCreated)
            .when(CustomerFirstNameUpdated.class, this::customerFirstNameUpdated)
            .mutate(event);
    }

    private void customerCreated(CustomerCreated event) {
        this.createCustomerInternal(event.aggregateId(),
                                    event.getFirstName(),
                                    event.getLastName(),
                                    event.getDateOfBirth());
    }

    private void customerFirstNameUpdated(CustomerFirstNameUpdated event) {
        this.updateFirstNameInternal(event.getFirstName());
    }


    private DomainEvent createCustomerInternal(String id,
                                               String firstName,
                                               String lastName,
                                               Date dateOfBirth) {
        this.id = id;
        this.status = Status.ACTIVE;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        return new CustomerCreated(this.id(),
                                   this.firstName(),
                                   this.lastName(),
                                   this.dateOfBirth());
    }

    private Optional<DomainEvent> updateFirstNameInternal(String firstName) {
        if (firstName.equals(this.firstName())) {
            log.warn("Customer {} first name is already {}. Will not update.",
                     this.id(), this.firstName());
            return Optional.empty();
        } else {
            this.firstName = firstName;
            return Optional.of(new CustomerFirstNameUpdated(this.id(), this.firstName()));
        }
    }
}
