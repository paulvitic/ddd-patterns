package net.vitic.ddd.readmodel.customerorder.application.service;

import lombok.AllArgsConstructor;
import net.vitic.ddd.readmodel.customerorder.domain.model.CustomerProjection;
import net.vitic.ddd.readmodel.customerorder.domain.repository.CustomerProjectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerOrderQueryService {

    private final CustomerProjectionRepository repository;

    Page<CustomerProjection> allOpenOrders(int page, int size){
        return repository.findAll(
            PageRequest.of(page,
                           size,
                           Sort.Direction.DESC,
                           "firstName"));
    }
}
