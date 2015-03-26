package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Camera
import com.msaas.model.Role
import com.msaas.model.User
import org.springframework.data.domain.Sort

import javax.annotation.Resource

import static com.msaas.model.CameraState.OFFLINE
import static com.msaas.model.CameraState.WAITING
import static org.springframework.data.domain.Sort.Direction.ASC

class CameraRepositoryTest extends AbstractIntegrationSpec {

    @Resource
    private CameraRepository cameraRepo
    @Resource
    private UserRepository userRepo
    private User customer
    private Date now

    def setup() {
        given: "a customer, some cameras"
        now = new Date()
        customer = new User(name: 'the customer', password: 'the password', role: Role.CUSTOMER)
        userRepo.save(customer)
    }

    def "should be able to find the cameras"() {
        given:
        (0..16).each {
            cameraRepo.save(new Camera(
                    name: "test camera$it",
                    customer: customer,
                    state: WAITING, url: "url$it",
                    nextViewingAt: now + it,
                    tags: '#some #tags',
                    startupDelay: 1))
        }
        when:
        def cameras = cameraRepo.findTop4ByState(WAITING, new Sort(ASC, 'nextViewingAt'))
        then:
        cameras.size() == 4
        cameras.each { cam -> assert cam.state == WAITING }
    }

    def "should fetch only WAITING cams"() {
        given:
        cameraRepo.deleteAll()
        (0..4).each { i ->
            cameraRepo.save(new Camera(
                    name: "test camera$i",
                    customer: customer,
                    state: OFFLINE, url: "url$i",
                    nextViewingAt: now + i,
                    tags: '#some #tags',
                    startupDelay: 1))
        }

        when:
        def cameras = cameraRepo.findTop4ByState(WAITING, new Sort(ASC, 'nextViewingAt'))
        then:
        cameras.size() == 0
    }
}