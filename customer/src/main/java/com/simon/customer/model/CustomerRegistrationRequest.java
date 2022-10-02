package com.simon.customer.model;

//mutability for free
public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {

}
