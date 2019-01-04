package net.vitic.ddd.readmodel.customer.port.adapter.message;

import net.vitic.ddd.config.AsyncConfig;
import net.vitic.ddd.infrastructure.ApplicationEventConsumer;
import net.vitic.ddd.readmodel.customer.domain.event.CustomerCreated;
import net.vitic.ddd.util.Behavior;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static net.vitic.ddd.readmodel.customer.ReadModelCustomerFixtures.*;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    ApplicationEventConsumer.class,
    AsyncConfig.class,
    DomainEventProducer.class})
class DomainEventProducerTest {

    @Autowired
    ApplicationEventConsumer applicationEventConsumer;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    EventSource eventSource;

    @MockBean
    MessageChannel messageChannel;

    @Test
    void should_do_something() {

        Behavior.given("first name {}, and last name {}, and birth date {}",
                       CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);

        Behavior.when("customer created event is published");
        when(eventSource.output()).thenReturn(messageChannel);
        applicationEventPublisher.publishEvent(customerCreatedEvent());

        await().atLeast(Duration.ONE_SECOND);

        Behavior.then("event should be consumed");
        verify(messageChannel).send(
            argThat((Message message)->
                        Objects.equals(message.getHeaders().get(DomainEventProducer.MSG_HEADER_EVENT_ID), CUSTOMER_ID) &&
                        Objects.equals(message.getHeaders().get(DomainEventProducer.MSG_HEADER_EVENT_TYPE), CustomerCreated.class.getName() + ":0") &&
                        message.getPayload().getClass().getName().equals(CustomerCreated.class.getName()) &&
                        ((CustomerCreated) message.getPayload()).getFirstName().equals(CUSTOMER_FIRST_NAME)));

        applicationEventConsumer.preStop();
        await().until(() -> applicationEventConsumer.isStopped());

        Behavior.success();
    }
}