package com.msaas.infrastructure

import com.msaas.AbstractIntegrationSpec
import com.msaas.model.Role
import com.msaas.model.Screen
import com.msaas.model.User

import javax.annotation.Resource

class UserRepositoryTest extends AbstractIntegrationSpec {

    @Resource
    private UserRepository userRepo
    private User obs
    private Date now

    def setup() {
        given: " insert a test observer and a test mobile device/token "
        obs = new User(
                name: 'Darth Vader',
                password: 'test',
                role: Role.OBSERVER
        )
        now = new Date()
        userRepo.save(obs)
    }

    def "should find an observer by name"() {
        expect:
        def opt = userRepo.findByName('Darth Vader')
        opt.isPresent()
        opt.get() == obs
    }

    def "should create a new last screen"() {
        when:
        Screen screen = obs.getLastScreen()
        then:
        screen
        screen.observer == obs
        obs.screens == [screen]
    }

    def "should return the last screen when there are some already"() {
        given:
        def lastScreen = new Screen(observer: obs, id: 3, scheduledAt: now + 2)
        obs.screens = [
                new Screen(observer: obs, id: 1, scheduledAt: now),
                new Screen(observer: obs, id: 2, scheduledAt: now + 1),
                lastScreen
        ]
        when:
        def screen = obs.getLastScreen()
        then:
        screen == lastScreen
    }
}