package net.vitic.ddd.readmodel.customer.domain.repository;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import net.vitic.ddd.util.Behavior;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldBeSaved() {
        final Behavior behavior = Behavior.given(
            "customer");
        Customer customer = Customer.create("", "", new Date());

        behavior.when("it is saved");
        customerRepository.save(customer);

        behavior.then("it should be waiting assignment.");
        Optional<Customer> saved = customerRepository.findById(customer.id());
        assertTrue(saved.isPresent(), behavior.fail());

        log.info(behavior.success());
    }

}