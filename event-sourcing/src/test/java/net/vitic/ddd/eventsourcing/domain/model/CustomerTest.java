package net.vitic.ddd.eventsourcing.domain.model;

import net.vitic.ddd.common.DomainEventListenerTest;
import net.vitic.ddd.common.util.Behavior;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.eventsourcing.domain.event.CustomerCreated;
import net.vitic.ddd.eventsourcing.domain.event.CustomerFirstNameUpdated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static net.vitic.ddd.common.util.Behavior.*;
import static net.vitic.ddd.eventsourcing.EventSourcingFixtures.CUSTOMER_FIRST_NAME_1;
import static net.vitic.ddd.eventsourcing.EventSourcingFixtures.customerCreatedEvent;
import static net.vitic.ddd.eventsourcing.EventSourcingFixtures.customerFirstNameUpdatedEvent;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CustomerTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public DomainEventListenerTest domainEventListener() {
            return new DomainEventListenerTest();
        }
    }

    @Autowired
    DomainEventListenerTest domainEventListener;

    @Test
    void customer_should_be_reconstructed_without_generating_domain_events() {

        given("two domain events {} and {}",
              CustomerCreated.class.getSimpleName(),
              CustomerFirstNameUpdated.class.getSimpleName());
        List<DomainEvent> events = Arrays.asList(
            customerCreatedEvent(),
            customerFirstNameUpdatedEvent());

        when("a customer is reconstructed from them");
        Customer customer = new Customer(events);

        then("it's first name should be {}", CUSTOMER_FIRST_NAME_1);
        assertEquals(CUSTOMER_FIRST_NAME_1, customer.firstName(), Behavior.fail());

        andThen("no domain event should be generated");
        assertEquals(0, domainEventListener.getDomainEvents().size(), Behavior.fail());

        success();
    }
}