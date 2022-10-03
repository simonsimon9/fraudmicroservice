package com.simon.customer.service;

import com.simon.customer.model.Customer;
import com.simon.customer.model.CustomerRegistrationRequest;
import com.simon.customer.model.FraudCheckResponse;
import com.simon.customer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService{
    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;
    @Autowired
    CustomerService(RestTemplate restTemplate, CustomerRepository customerRepository){
        this.restTemplate = restTemplate;
        this.customerRepository = customerRepository;
    }
    public void registerCustomer(CustomerRegistrationRequest request) throws IllegalAccessException {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer); //makes it avaible because run async but in orrder

        //todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalAccessException("fraudster");
        }
        //////no//todo: save to repo
        //customerRepository.save(customer);

        //todo: send notification
    }
}
