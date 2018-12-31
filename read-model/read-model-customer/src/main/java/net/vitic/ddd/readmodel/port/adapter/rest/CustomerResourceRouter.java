package net.vitic.ddd.readmodel.port.adapter.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CustomerResourceRouter {

    static final String PATH_CUSTOMERS = "/customers";

    @Bean
    public RouterFunction<ServerResponse> route(CustomerResourceHandler handler) {
        return RouterFunctions
            .route(GET(PATH_CUSTOMERS).and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
            .andRoute(GET(PATH_CUSTOMERS + "/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
            .andRoute(POST(PATH_CUSTOMERS).and(accept(MediaType.APPLICATION_JSON)), handler::save)
            .andRoute(PUT(PATH_CUSTOMERS + "/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::update)
            .andRoute(DELETE(PATH_CUSTOMERS + "/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }
}
