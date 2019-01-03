package net.vitic.ddd.readmodel.customer.port.adapter.rest;

import net.vitic.ddd.readmodel.customer.application.command.CreateCustomerCommand;
import net.vitic.ddd.readmodel.customer.application.service.CustomerService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static net.vitic.ddd.readmodel.customer.ReadModelCustomerFixtures.*;
import static net.vitic.ddd.util.Behavior.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ComponentScan({"net.vitic.ddd.readmodel.customer.port.adapter.rest" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CustomerApiRouterTest {

    @Autowired
    private CustomerResourceRouter customerApiRouter;

    @Autowired
    private CustomerResourceHandler customerResourceHandler;

    @MockBean
    private CustomerService customerService;

    @Test
    void should_create_customer() {

        given("first name {}, and last name {}, and birth date {}",
              CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);
        Mockito.when(customerService.create(any(CreateCustomerCommand.class))).thenReturn(CUSTOMER_ID);

        when("create customer request is sent");
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(
            CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_BIRTH_DATE);

        WebTestClient client = WebTestClient
            .bindToRouterFunction(customerApiRouter.route(customerResourceHandler))
            .build();

        WebTestClient.ResponseSpec response = client.post()
              .uri(CustomerResourceRouter.PATH_CUSTOMERS)
              .body(Mono.just(createCustomerRequest), CreateCustomerRequest.class)
              .exchange();

        then("customer service should be invoked with create customer command");
        verify(customerService).create(argThat(
            (CreateCustomerCommand command) ->
                        command.getFirstName().equals(CUSTOMER_FIRST_NAME) &&
                        command.getFirstName().equals(CUSTOMER_FIRST_NAME) &&
                        command.getDateOfBirth().getTime()==CUSTOMER_BIRTH_DATE.getTime()));

        andThen("OK status should be returned and body should be equal to {}", CUSTOMER_ID);
        response.expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(CUSTOMER_ID);

        success();
    }

    @Test
    void should_return_not_found_on_delete_error(){

        given("a non-existing customer id {}", CUSTOMER_ID);

        String exceptionMsg = "Customer not found.";
        andGiven("and exception message for non-existing customers, {}", exceptionMsg);
        doThrow(new RuntimeException(exceptionMsg)).when(customerService).delete(CUSTOMER_ID);

        when("delete customer request is sent");
        WebTestClient client = WebTestClient
            .bindToRouterFunction(customerApiRouter.route(customerResourceHandler))
            .build();
        WebTestClient.ResponseSpec response = client.delete()
                                                    .uri(CustomerResourceRouter.PATH_CUSTOMERS + "/" + CUSTOMER_ID)
                                                    .exchange();

        then("delete method should be invoked at customer service");
        verify(customerService).delete(argThat((String id) ->id.equals(CUSTOMER_ID)));

        andThen("and not found status should be returned with exception error message, {}", exceptionMsg);
        response.expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class)
                .isEqualTo(new ErrorResponse(exceptionMsg));

        success();
    }

    @Test
    void should_update_customer_name(){

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(
            UpdateCustomerRequest.Type.FIRST_NAME, CUSTOMER_FIRST_NAME);

        WebTestClient client = WebTestClient
            .bindToRouterFunction(customerApiRouter.route(customerResourceHandler))
            .build();

        WebTestClient.ResponseSpec response = client.put()
                                                    .uri(CustomerResourceRouter.PATH_CUSTOMERS + "/" + CUSTOMER_ID)
                                                    .body(Mono.just(updateCustomerRequest), UpdateCustomerRequest.class)
                                                    .exchange();

        success();
    }
}