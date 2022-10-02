package com.simon.customer.controller;

import com.simon.customer.model.CustomerRegistrationRequest;
import com.simon.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequestRequest){
        log.info("new customer registration {}", customerRegistrationRequestRequest);
        customerService.registerCustomer(customerRegistrationRequestRequest);
    }
}
