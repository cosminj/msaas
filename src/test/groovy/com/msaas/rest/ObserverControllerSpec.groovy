package com.msaas.rest

import com.msaas.AbstractIntegrationSpec
import com.msaas.infrastructure.CameraRepository
import com.msaas.infrastructure.CustomerRepository
import com.msaas.infrastructure.ObserverRepository
import com.msaas.infrastructure.ScreenRepository
import com.msaas.model.Camera
import com.msaas.model.Customer
import com.msaas.model.Observer
import org.springframework.beans.factory.annotation.Autowired

import static com.msaas.model.CameraState.WAITING

class ObserverControllerSpec extends AbstractIntegrationSpec {

    @Autowired
    private ObserverController observerController
    @Autowired
    private CustomerRepository customerRepo
    @Autowired
    private CameraRepository cameraRepo
    @Autowired
    private ScreenRepository screenRepo
    @Autowired
    private ObserverRepository observerRepo

    private Customer customer
    private Date now
    private Observer observer

    def setup() {
        given: "a customer, some cameras, existing screen, an observer"
        now = new Date()
        customer = customerRepo.save(new Customer(name: 'the customer', password: 'the password'))
        (0..20).each { int i ->
            cameraRepo.save(new Camera(name: "camera$i", customer: customer, state: WAITING, url: "url$i", nextViewingAt: now + i))
        }
        observer = observerRepo.save(new Observer(name: 'some test observer', password: 'observers password', state: 'ONLINE'))
    }

    def "should mark the last screen as viewed" () {
        when:
        observerController.markLastScreenViewed(observer)
        then:
        observer.screens.size() == 1
    }


}