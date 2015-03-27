package com.msaas.rest

import com.msaas.infrastructure.CameraRepository
import com.msaas.infrastructure.UserRepository
import com.msaas.model.User
import spock.lang.Specification

class CustomerControllerTest extends Specification {

    private CustomerController controller
    private UserRepository userRepo
    private CameraRepository cameraRepo

    def setup() {
        userRepo = Mock(UserRepository)
        cameraRepo = Mock(CameraRepository)
        controller = new CustomerController(userRepository: userRepo, cameraRepository: cameraRepo)
    }

    def "A logged in customer should be able to access all his cameras" () {
        given:
        org.springframework.security.core.userdetails.User cust = Mock(org.springframework.security.core.userdetails.User)
        cust.getUsername() >> 'some test cust'
        def customer = new User()
        def set2
        when:
        def set = controller.customerCameras(cust)
        then:
        1 * userRepo.findByName('some test cust') >> Optional.of(customer)
        1 * cameraRepo.findByCustomer(customer) >> set2
        set == set2
    }
}