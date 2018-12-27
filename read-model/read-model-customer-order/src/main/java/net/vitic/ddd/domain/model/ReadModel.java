package net.vitic.ddd.domain.model;

import net.vitic.ddd.domain.event.DomainEvent;

public interface ReadModel {

    void mutate(DomainEvent event);
}

