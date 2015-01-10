package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Customer
import org.springframework.beans.factory.annotation.Autowired

class CustomerRepositorySpec extends AbstractIntegrationSpec {

    @Autowired
    private CustomerRepository customerRepository

    private Customer testCustomer

    def setup() {
        given: " insert a test customer and a test mobile device/token "
        testCustomer = new Customer(
                name: 'Darth Vader',
                password: 'test'
        )
        customerRepository.save(testCustomer)
    }

    def "should find a customer by name" () {
        expect:
        customerRepository.findByName('Darth Vader') == testCustomer
    }

}