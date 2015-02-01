package com.msaas.rest;

import com.msaas.infrastructure.CustomerRepository;
import com.msaas.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/server/customerDetails")
    public Customer getMyDetails(@AuthenticationPrincipal User user) {
        return customerRepository.findByName(user.getUsername());
    }
}