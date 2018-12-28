package net.vitic.ddd.readmodel.customer.domain.repository;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.readmodel.customer.DomainEventListenerTest;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerCreated;
import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import net.vitic.ddd.util.Behavior;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public DomainEventListenerTest domainEventListener() {
            return new DomainEventListenerTest();
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DomainEventListenerTest domainEventListener;

    @Test
    void should_save_customer() {

        String firstName = "firstName";
        final Behavior behavior = Behavior.given(
            "a first name " + firstName);

        String lastName = "lastName";
        behavior.andGiven("and a last name " + lastName);

        Date dateOfBirth = new Date();
        behavior.andGiven("and a birth date " + dateOfBirth);

        behavior.when("a customer is created with these parameters");
        Customer customer = Customer.create(firstName, lastName, dateOfBirth);

        behavior.andWhen("and it is saved");
        customerRepository.save(customer);

        behavior.then("a single domain event should be generated");
        assertEquals(1, domainEventListener.getDomainEvents().size(), behavior.fail());

        behavior.andThen("type of domain event should be " + CustomerCreated.class.getSimpleName());
        assertEquals(CustomerCreated.class.getSimpleName(),
                     domainEventListener.getDomainEvents().get(0).type(), behavior.fail());

        behavior.andThen("saved customer should be retrievable from repository by id " + customer.id());
        Optional<Customer> saved = customerRepository.findById(customer.id());
        assertTrue(saved.isPresent(), behavior.fail());

        behavior.andThen("and it's status should be " + Customer.Status.ACTIVE);
        assertEquals(Customer.Status.ACTIVE, saved.get().status(), behavior.fail());

        behavior.andThen("and it's first name should be " + firstName);
        assertEquals(firstName, saved.get().firstName(), behavior.fail());

        behavior.andThen("and it's last name should be " + lastName);
        assertEquals(lastName, saved.get().lastName(), behavior.fail());

        behavior.andThen("and it's birth date should be " + dateOfBirth);
        assertEquals(dateOfBirth.getTime(), saved.get().dateOfBirth().getTime(), behavior.fail());

        log.info(behavior.success());
    }
}