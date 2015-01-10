package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Observer
import org.springframework.beans.factory.annotation.Autowired

class ObserverRepositorySpec extends AbstractIntegrationSpec {

    @Autowired
    private ObserverRepository observerRepository

    private Observer testObs

    def setup() {
        given: " insert a test customer and a test mobile device/token "
        testObs = new Observer(
                name: 'Darth Vader',
                password: 'test',
                state: 'ONLINE'
        )
        observerRepository.save(testObs)
    }

    def "should find a customer by name"() {
        expect:
        observerRepository.findByName('Darth Vader') == testObs
    }

}