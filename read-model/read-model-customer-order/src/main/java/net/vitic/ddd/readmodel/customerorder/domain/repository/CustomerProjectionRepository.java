package net.vitic.ddd.readmodel.customerorder.domain.repository;

import net.vitic.ddd.readmodel.customerorder.domain.model.CustomerProjection;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerProjectionRepository extends PagingAndSortingRepository<CustomerProjection, String> {
}
