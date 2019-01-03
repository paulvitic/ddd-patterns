package net.vitic.ddd.readmodel.customer.port.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
class UpdateCustomerRequest {

    enum Type { FIRST_NAME, LAST_NAME, BIRTHDATE}

    @NonNull
    Type type;
    String name;
    Date dateOfBirth;

    public UpdateCustomerRequest(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public UpdateCustomerRequest(Type type, Date dateOfBirth) {
        this.type = type;
        this.dateOfBirth = dateOfBirth;
    }
}
