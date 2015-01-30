package com.msaas.rest

import com.msaas.AbstractIntegrationSpec
import com.msaas.infrastructure.CameraRepository
import com.msaas.infrastructure.CustomerRepository
import com.msaas.infrastructure.ObserverRepository
import com.msaas.infrastructure.ScreenRepository
import com.msaas.model.Camera
import com.msaas.model.Customer
import com.msaas.model.Observer
import com.msaas.model.Screen
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService

import static com.msaas.model.CameraState.SCHEDULED
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
    @Autowired
    private UserDetailsService userDetailsService

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
        given:
        Screen lastScreen = observer.lastScreen
        when:
        observerController.markLastScreenViewed(lastScreen)
        then:
        lastScreen.viewedAt
    }

    def "should compute next screen" () {
        when:
        Screen screen = observerController.computeNextScreen(observer)
        then:
        screen
        screen.cameras.size() == 4
        screen.cameras.each { c ->
            assert c.state == SCHEDULED
            assert c.nextViewingAt == screen.scheduledAt
        }
    }

    def "should have a next screen" () {
        given:
        def user = userDetailsService.loadUserByUsername 'Cosmin'
        expect:
        observerController.nextScreen(user)
    }
}