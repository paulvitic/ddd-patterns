package net.vitic.ddd.readmodel.application.service;

import lombok.Value;

import java.util.Date;

@Value
public class CreateCustomerCommand {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
}
