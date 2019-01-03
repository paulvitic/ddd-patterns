package net.vitic.ddd.readmodel.customer.port.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateCustomerRequest {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date dateOfBirth;
}
