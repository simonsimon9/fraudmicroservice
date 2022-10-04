package com.simon.customer.service;

import com.simon.clients.fraud.FraudCheckResponse;
import com.simon.clients.fraud.FraudClient;
import com.simon.clients.notification.NotificationClient;
import com.simon.clients.notification.NotificationRequest;
import com.simon.customer.model.Customer;
import com.simon.customer.model.CustomerRegistrationRequest;
import com.simon.customer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.simon.clients.notification.NotificationClient;

@Service
public class CustomerService{
    private final NotificationClient notificationClient;
    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;
    @Autowired
    CustomerService(FraudClient fraudClient, NotificationClient notificationClient, CustomerRepository customerRepository){
        this.notificationClient = notificationClient;
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
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

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalAccessException("fraudster");
        }
        //////no//todo: save to repo
        //customerRepository.save(customer);

        //todo: send notification
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("hi %s, welcome", customer.getFirstName())
                )
        );
    }
}

//todo: check if fraudster using rest template , and add eureka url
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        ); old way calling directly from microservice. below is using the open feig
//line 34
