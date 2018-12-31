package net.vitic.ddd.readmodel.port.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.readmodel.application.service.CreateCustomerCommand;
import net.vitic.ddd.readmodel.application.service.CustomerService;
import net.vitic.ddd.readmodel.application.service.UpdateCustomerCommand;
import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerResourceHandler {

    private final CustomerService customerService;

    Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(Mono.justOrEmpty(customerService.find(id)), Customer.class)
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Flux.fromIterable(customerService.findAll()), Customer.class);
    }

    Mono<ServerResponse> save(ServerRequest request) {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request.bodyToMono(CreateCustomerRequest.class)
                         .map(this::verifyAndTranslate), String.class);
    }


    Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(UpdateCustomerRequest.class)
                      .doOnNext(this::verifyAndTranslate)
                      .then(ok().build())
                      .doOnError(e -> Mono.just(
                          new ErrorResponse(e.getMessage()))
                              .flatMap(body -> status(HttpStatus.NOT_FOUND)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .syncBody(body)));
    }


    Mono<ServerResponse> delete(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                   .doOnNext(customerService::delete)
                   .flatMap(body -> ok()
                       .contentType(MediaType.APPLICATION_JSON)
                       .syncBody(body))
                   .onErrorResume(e -> Mono.just(new ErrorResponse(e.getMessage()))
                       .flatMap(body -> status(HttpStatus.NOT_FOUND)
                           .contentType(MediaType.APPLICATION_JSON)
                           .syncBody(body)));
    }

    private String verifyAndTranslate(CreateCustomerRequest request) {
        CreateCustomerCommand command = new CreateCustomerCommand(
                                                request.getFirstName(),
                                                request.getLastName(),
                                                request.getDateOfBirth());
        return customerService.create(command);
    }

    private void verifyAndTranslate(UpdateCustomerRequest request) {
        switch (request.type) {
            case FIRST_NAME:
            case LAST_NAME:
            case BIRTHDATE:
            default:
        }
        UpdateCustomerCommand command = new UpdateCustomerCommand();
        customerService.update(command);
    }
}
