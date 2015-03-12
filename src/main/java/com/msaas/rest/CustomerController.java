package com.msaas.rest;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaas.infrastructure.CustomerRepository;
import com.msaas.model.Customer;

@RestController
public class CustomerController {

    @Resource
    private CustomerRepository customerRepository;

    @RequestMapping("/server/customerDetails")
    public Customer getMyDetails(@AuthenticationPrincipal User user) {
        return customerRepository.findByName(user.getUsername());
    }
}