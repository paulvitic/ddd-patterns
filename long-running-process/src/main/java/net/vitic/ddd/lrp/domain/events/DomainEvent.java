package net.vitic.ddd.lrp.domain.events;

import java.util.Date;

public interface DomainEvent {

    int eventVersion();

    Date occurredOn();

    String processId();

    String type();

    boolean local();
}
