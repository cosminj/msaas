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

/**
 * @author cj 
 * @since 30/12/14
 */
@RestController
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/customerDetails")
    public Customer getMyDetails(@AuthenticationPrincipal User user) {
        logger.info("User get my details {}", user);
        return customerRepository.findByName(user.getUsername());
    }
}