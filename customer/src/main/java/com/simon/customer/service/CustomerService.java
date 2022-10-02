package com.simon.customer.service;

import com.simon.customer.model.Customer;
import com.simon.customer.model.CustomerRegistrationRequest;
import com.simon.customer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{
    private final CustomerRepository customerRepository;
    @Autowired
    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
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
