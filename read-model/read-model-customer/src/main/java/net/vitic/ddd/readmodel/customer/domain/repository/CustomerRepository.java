package net.vitic.ddd.readmodel.customer.domain.repository;

import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
}
