package com.simon.fraud.controller;

import com.simon.fraud.model.FraudCheckResponse;
import com.simon.fraud.service.FraudCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {
    private final FraudCheckService fraudCheckService;

    @Autowired
    FraudController(FraudCheckService fraudCheckService){
        this.fraudCheckService = fraudCheckService;
    }
    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId){
        boolean isFraudulentCustomer = fraudCheckService.isFraudualentCustomer(customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
