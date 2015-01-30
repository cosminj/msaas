package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Camera
import com.msaas.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort

import static com.msaas.model.CameraState.WAITING
import static org.springframework.data.domain.Sort.Direction.ASC

class CameraRepositorySpec extends AbstractIntegrationSpec {

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

        (0..20).each { int i ->
            cameraRepo.save(new Camera(name: "test camera$i", customer: customer, state: WAITING, url: "url$i", nextViewingAt: now + i))
        }
    }

    def "should be able to find the cameras"() {
        when:
        def list = cameraRepo.findTop4ByState(WAITING, new Sort(ASC, 'nextViewingAt'))
        then:
        list.size() == 4
        (0..3).each { int i ->
            assert list[i].nextViewingAt == now + i
            assert list[i].url == "url$i"
            assert list[i].name == "camera$i"
        }
    }
}