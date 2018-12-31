package net.vitic.ddd.readmodel.application.service;

import lombok.AllArgsConstructor;
import net.vitic.ddd.readmodel.customer.domain.model.Customer;
import net.vitic.ddd.readmodel.customer.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Customer find(String id) {
        return customerRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Transactional
    public String create(CreateCustomerCommand command) {
        return customerRepository.save(
                Customer.create(command.getFirstName(), command.getLastName(), command.getDateOfBirth()))
                .id();
    }

    @Transactional
    public void update(UpdateCustomerCommand command) {

    }

    @Transactional
    public void delete(String id) {
        customerRepository.deleteById(id);
    }


}
