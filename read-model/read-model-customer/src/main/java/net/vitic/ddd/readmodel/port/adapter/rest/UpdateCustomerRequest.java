package net.vitic.ddd.readmodel.port.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
class UpdateCustomerRequest {

    enum Type { FIRST_NAME, LAST_NAME, BIRTHDATE}

    @NonNull
    Type type;
    String name;
    Date dateOfBirth;
}
