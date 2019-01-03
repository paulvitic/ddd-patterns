package net.vitic.ddd.readmodel.customerorder.domain.model;

import lombok.RequiredArgsConstructor;
import net.vitic.ddd.readmodel.customerorder.domain.ReadModel;
import net.vitic.ddd.readmodel.customerorder.domain.event.CustomerAdded;
import net.vitic.ddd.readmodel.customerorder.domain.event.CustomerRemoved;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.readmodel.customerorder.domain.repository.CustomerProjectionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomerOrder implements ReadModel {

    private final CustomerProjectionRepository repository;

    @Override
    @Transactional
    @EventListener
    public void mutate(DomainEvent event) {
        select()
            .when(CustomerAdded.class, this::addCustomer)
            .when(CustomerRemoved.class, this::removeCustomer)
            .mutate(event);
    }


    private void addCustomer(CustomerAdded event){
        repository.save(CustomerProjection.create(
            event.aggregateId(),
            event.getFirstName(),
            event.getLastName()));
    }

    private void removeCustomer(CustomerRemoved event) {
        repository.deleteById(event.aggregateId());
    }
}