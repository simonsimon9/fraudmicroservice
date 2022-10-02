package com.simon.customer.service;

import com.simon.customer.model.Customer;
import com.simon.customer.model.CustomerRegistrationRequest;
import com.simon.customer.repo.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check email valid
        //todo: check if email not taken

        customerRepository.save(customer);
    }
}
