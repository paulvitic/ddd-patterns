package net.vitic.ddd.lrp.application.command;

import lombok.Getter;

import java.util.List;

@Getter
public class StartProcessCommand {

    private final List<String> phoneNumbers;

    public StartProcessCommand(List<String> phoneNumbers) {this.phoneNumbers = phoneNumbers;}
}
