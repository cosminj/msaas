package com.msaas.service

import com.msaas.AbstractIntegrationSpec
import com.msaas.infrastructure.CameraRepository
import com.msaas.infrastructure.UserRepository
import com.msaas.model.Camera
import com.msaas.model.Role
import com.msaas.model.Screen
import com.msaas.model.User

import javax.annotation.Resource

import static com.msaas.model.CameraState.SCHEDULED
import static com.msaas.model.CameraState.WAITING

class ScreenServiceTest extends AbstractIntegrationSpec {

    @Resource
    private CameraRepository cameraRepo
    @Resource
    private UserRepository userRepo
    @Resource
    private ScreenService screenService
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

    def "should scroll to next screen"() {
        when:
        Screen screen = screenService.scrollNextScreen(observer)
        then:
        screen.cameras.size() == 4
        screen.cameras.each { c ->
            assert c.state == SCHEDULED
            assert c.nextViewingAt == screen.scheduledAt
        }
    }

    def "should compute next screen"() {
        when:
        Screen screen = screenService.computeNextScreen(observer)
        then:
        screen
        screen.cameras.size() == 4
        screen.cameras.each { c ->
            assert c.state == SCHEDULED
            assert c.nextViewingAt == screen.scheduledAt
        }
    }
}