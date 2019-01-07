package net.vitic.ddd.eventsourcing.application.service;

import lombok.Value;

@Value
public class ChangeCustomerFirstNameCmd {
    String agrregateId;
    String name;
}
