package net.vitic.ddd.eventsourcing.domain.model;

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


    public static DomainEvent create(@NonNull String firstName,
                                     @NonNull String lastName,
                                     @NonNull Date dateOfBirth) {

        return new CustomerCreated(EntityIdGenerator.generate(),
                                   firstName,
                                   lastName,
                                   dateOfBirth);
    }

    public Optional<DomainEvent> updateFirstName(String firstName){
        if (firstName.equals(this.firstName())) {
            log.warn("Customer {} first name is already {}. Will not update.",
                     this.id(), this.firstName());
            return Optional.empty();
        } else {
            this.firstName = firstName;
            return Optional.of(new CustomerFirstNameUpdated(this.id(), this.firstName()));
        }
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
        this.id = event.aggregateId();
        this.status = Status.ACTIVE;
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dateOfBirth = event.getDateOfBirth();
    }

    private void customerFirstNameUpdated(CustomerFirstNameUpdated event) {
        this.updateFirstName(event.getFirstName());
    }
}
