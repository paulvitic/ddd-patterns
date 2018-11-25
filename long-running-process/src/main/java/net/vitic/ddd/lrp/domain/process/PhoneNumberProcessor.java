package net.vitic.ddd.lrp.domain.process;

import net.vitic.ddd.lrp.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberProcessor<T extends DomainEvent> {

    Optional<DomainEvent> process(T event);

    List<String> handles();
}
