package net.vitic.ddd.eventsourcing.application.service;

import lombok.Value;

import java.util.Date;

@Value
public class CreateCustomerCmd {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}
