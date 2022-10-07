package com.simon.customer.service;

import com.simon.amqp.RabbitMQMessageProducer;
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
    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    @Autowired
    CustomerService(FraudClient fraudClient,CustomerRepository customerRepository, RabbitMQMessageProducer rabbitMQMessageProducer){
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
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
        NotificationRequest notificationRequest = new NotificationRequest(
                     customer.getId(),
                     customer.getEmail(),
                    String.format("hi %s, welcome", customer.getFirstName())
              );


        //todo: send notification
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
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
