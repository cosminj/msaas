package com.msaas.model

import spock.lang.Specification


class ObserverSpec extends Specification {

    private Observer observer
    private Date now

    def setup() {
        given: "build an instance of observer"
        observer = new Observer()
        now = new Date()
    }

    def "should create a new last screen"() {
        when:
        def screen = observer.getLastScreen()
        then:
        screen
        screen.observer == observer
        observer.screens == [screen]
    }

    def "should return the last screen when there are some already"() {
        given:
        def lastScreen = new Screen(observer: observer, id: 3, scheduledAt: now + 2)
        observer.screens = [
                new Screen(observer: observer, id: 1, scheduledAt: now),
                new Screen(observer: observer, id: 2, scheduledAt: now + 1),
                lastScreen
        ]
        when:
        def screen = observer.getLastScreen()
        then:
        screen == lastScreen
    }

}
