package net.vitic.ddd.readmodel.customerorder.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerProjection {

    private String id;
    private String firstName;
    private String lastName;
    private Set<OrderProjection> orders;

    static CustomerProjection create(String id, String firstName, String lastName){
        return new CustomerProjection(id, firstName, lastName, new HashSet<>());
    }

    public String id(){
        return this.id;
    }
}
