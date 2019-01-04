package net.vitic.ddd.readmodel.customerorder.domain.model;

import net.vitic.ddd.readmodel.customerorder.domain.event.CustomerAdded;
import net.vitic.ddd.readmodel.customerorder.domain.repository.CustomerProjectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static net.vitic.ddd.readmodel.customerorder.ReadModelCustomerOrderFixtures.CUSTOMER_ID;
import static net.vitic.ddd.readmodel.customerorder.ReadModelCustomerOrderFixtures.customerAddedEvent;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CustomerOrder.class})
class CustomerOrderTest {

    @MockBean
    CustomerProjectionRepository repository;

    @Autowired
    CustomerOrder customerOrder;

    @Test
    void customer_added_event_should_only_save_customer() {
        customerOrder.mutate(customerAddedEvent());
        verify(repository)
            .save(argThat((CustomerProjection customer) -> customer.id().equals(CUSTOMER_ID)));
        verify(repository, never()).deleteById(anyString());
    }
}