package net.vitic.ddd.readmodel.customer.domain.repository;

import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;

interface CustomerRepository extends CrudRepository<Customer, Long> {
}
