package net.vitic.ddd.readmodel.customer.domain.model;

import lombok.Builder;
import lombok.NonNull;
import net.vitic.ddd.domain.model.AggregateRoot;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerCreated;
import net.vitic.ddd.util.JpaIdGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(value = JpaIdGenerator.class)
public class Customer extends AggregateRoot {

    public enum Status {ACTIVE, DELETED}

    @Id
    private Long id;

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
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.status = Status.ACTIVE;
        registerEvent(new CustomerCreated(Long.toString(this.id),
                                          this.firstName,
                                          this.lastName,
                                          this.dateOfBirth));
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

    public Long id() {
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
}
