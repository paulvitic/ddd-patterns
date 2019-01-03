package net.vitic.ddd.readmodel.customer.domain.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.model.AggregateRoot;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerCreated;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerFirstNameUpdated;
import net.vitic.ddd.util.EntityIdGenerator;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@Entity
public class Customer extends AggregateRoot {

    public enum Status {ACTIVE, SUSPENDED, DELETED}

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Version
    private Integer version;

    private String firstName;

    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    private Customer(@NonNull String firstName,
                     @NonNull String lastName,
                     @NonNull Date dateOfBirth) {
        this.id = EntityIdGenerator.generate();
        this.status = Status.ACTIVE;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        registerEvent(new CustomerCreated(this.id(),
                                          this.firstName(),
                                          this.lastName(),
                                          this.dateOfBirth()));
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

    public void updateFirstName(String firstName){
        if (firstName.equals(this.firstName())) {
            log.warn("Customer {} first name is already {}. Will not update.",
                     this.id(), this.firstName());
        } else {
            this.firstName = firstName;
            registerEvent(new CustomerFirstNameUpdated(this.id(), this.firstName()));
        }
    }

}
