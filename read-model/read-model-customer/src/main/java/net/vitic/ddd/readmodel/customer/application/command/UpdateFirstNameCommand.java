package net.vitic.ddd.readmodel.customer.application.command;

import lombok.Value;

@Value
public class UpdateFirstNameCommand {
    String id;
    String firstName;
}
