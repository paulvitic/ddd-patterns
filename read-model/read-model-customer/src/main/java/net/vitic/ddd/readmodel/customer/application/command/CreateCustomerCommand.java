package net.vitic.ddd.readmodel.customer.application.command;

import lombok.Value;

import java.util.Date;

@Value
public class CreateCustomerCommand {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
}
