package net.vitic.ddd.readmodel.customer.domain.repository;

import net.vitic.ddd.readmodel.customer.DomainEventListenerTest;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerCreated;
import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.vitic.ddd.readmodel.ReadModelTestFixtures.*;
import static net.vitic.ddd.util.Behavior.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
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
    void should_not_find_non_existing_customer(){
        given("non-existing customer {}", CUSTOMER_ID);

        when("it is searched in repository");
        Optional<Customer> res = customerRepository.findById(CUSTOMER_ID);

        then("result should be emty");
        assertFalse(res.isPresent(), fail());

        success();
    }

    @Test
    void should_save_customer() {

        given("first name {}, and last name {}, and birth date {}",
              CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);


        when("a customer is created, and it is saved");
        Customer customer = Customer.create(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);
        customerRepository.save(customer);


        then("a single domain event should be generated");
        assertEquals(1, domainEventListener.getDomainEvents().size(), fail());

        andThen("type of domain event should be {}", CustomerCreated.class.getSimpleName());
        assertEquals(CustomerCreated.class.getSimpleName(),
                     domainEventListener.getDomainEvents().get(0).type(), fail());

        andThen("saved customer should be retrievable from repository by {}", customer.id());
        Optional<Customer> saved = customerRepository.findById(customer.id());
        assertTrue(saved.isPresent(), fail());

        andThen("and it's status should be {}", Customer.Status.ACTIVE);
        assertEquals(Customer.Status.ACTIVE, saved.get().status(), fail());

        andThen("and it's first name should be {}", CUSTOMER_FIRST_NAME);
        assertEquals(CUSTOMER_FIRST_NAME, saved.get().firstName(), fail());

        andThen("and it's last name should be {}", CUSTOMER_LAST_NAME);
        assertEquals(CUSTOMER_LAST_NAME, saved.get().lastName(), fail());

        andThen("and it's birth date should be {}", CUSTOMER_BIRTH_DATE);
        assertEquals(CUSTOMER_BIRTH_DATE.getTime(), saved.get().dateOfBirth().getTime(), fail());

        success();
    }

    @Test
    void should_not_delete_non_existing_customer(){
        given("non-existing customer {}", CUSTOMER_ID);
        Customer customer = Customer.create(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);
        //customerRepository.save(customer);

        when("it is deleted");

        then("{} should be thrown", EmptyResultDataAccessException.class.getSimpleName());
        assertThrows(EmptyResultDataAccessException.class, () -> customerRepository.deleteById(CUSTOMER_ID), fail());

        success();
    }
}