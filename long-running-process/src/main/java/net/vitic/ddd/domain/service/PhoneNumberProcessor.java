package net.vitic.ddd.domain.service;

import net.vitic.ddd.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberProcessor<T extends DomainEvent> {

    Optional<DomainEvent> process(T event);

    List<String> handles();
}
