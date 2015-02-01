package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Camera
import com.msaas.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort

import static com.msaas.model.CameraState.OFFLINE
import static com.msaas.model.CameraState.WAITING
import static org.springframework.data.domain.Sort.Direction.ASC

class CameraRepositoryTest extends AbstractIntegrationSpec {

    @Autowired
    private CameraRepository cameraRepo
    @Autowired
    private CustomerRepository customerRepo

    private Customer customer
    private Date now

    def setup() {
        given: "a customer, some cameras"
        now = new Date()
        customer = new Customer(name: 'the customer', password: 'the password')
        customerRepo.save(customer)


    }

    def "should be able to find the cameras"() {
        given:
        (0..16).each { i ->
            cameraRepo.save(new Camera(
                    name: "test camera$i",
                    customer: customer,
                    state: WAITING, url: "url$i",
                    nextViewingAt: now + i,
                    tags: '#some #tags',
                    startupDelay: 1))
        }

        when:
        def cameras = cameraRepo.findTop4ByState(WAITING, new Sort(ASC, 'nextViewingAt'))
        then:
        cameras.size() == 4
        cameras.each { cam -> assert cam.state == WAITING }
    }

    def "should fetch only WAITING cams" () {
        given:
        cameraRepo.deleteAll()
        (0..4).each { i -> cameraRepo.save(new Camera(
                name: "test camera$i",
                customer: customer,
                state: OFFLINE, url: "url$i",
                nextViewingAt: now + i,
                tags: '#some #tags',
                startupDelay: 1))}

        when:
        def cameras = cameraRepo.findTop4ByState(WAITING, new Sort(ASC, 'nextViewingAt'))
        then:
        cameras.size() == 0
//        cameras.each { cam -> assert cam.state == WAITING }
    }
}