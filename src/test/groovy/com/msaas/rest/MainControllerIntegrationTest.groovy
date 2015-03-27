package com.msaas.rest

import com.msaas.AbstractIntegrationSpec
import com.msaas.infrastructure.CameraRepository
import com.msaas.infrastructure.ScreenRepository
import com.msaas.infrastructure.UserRepository
import com.msaas.model.Camera
import com.msaas.model.Role
import com.msaas.model.Screen
import com.msaas.model.User
import org.springframework.security.core.userdetails.UserDetailsService

import javax.annotation.Resource

import static com.msaas.model.CameraState.WAITING

class MainControllerIntegrationTest extends AbstractIntegrationSpec {

    @Resource
    private MainController mainController
    @Resource
    private UserRepository userRepo
    @Resource
    private CameraRepository cameraRepo
    @Resource
    private ScreenRepository screenRepo
    @Resource
    private UserDetailsService userDetailsService

    private User customer
    private Date now
    private User observer

    def setup() {
        given: "a customer, some cameras, existing screen, an observer"
        now = new Date()
        customer = userRepo.save(new User(name: 'the customer', password: 'the password', role: Role.CUSTOMER))
        (0..20).each {
            cameraRepo.save(new Camera(name: "camera$it",
                    customer: customer,
                    state: WAITING,
                    url: "url$it",
                    nextViewingAt: now + it,
                    tags: '#some #tags',
                    startupDelay: 1))
        }
        observer = userRepo.save(new User(name: 'some test observer', password: 'observers password', role: Role.OBSERVER))
    }

    def "should have a next screen"() {
        given:
        Screen screen
        org.springframework.security.core.userdetails.User observer = Mock(org.springframework.security.core.userdetails.User)
        observer.getUsername() >> 'some test observer'
        when:
        screen = mainController.nextScreen(observer)
        then:
        screen
        screen.cameras.size() == 4
    }

    def "should fetch the details of a customer" () {
        given:
        org.springframework.security.core.userdetails.User observer = Mock(org.springframework.security.core.userdetails.User)
        observer.getUsername() >> 'some test observer'
        def user
        when:
        user = mainController.getMyDetails(observer)
        then:
        user
        user.name == 'some test observer'
        user.password == 'observers password'
    }
}