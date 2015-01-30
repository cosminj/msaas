package com.msaas.security

import com.msaas.AbstractIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class CustomerUserDetailsServiceTest extends AbstractIntegrationSpec {

    @Autowired
    private UserDetailsService userDetailsService

    def "should have a next screen"() {
        expect:
        UserDetails user = userDetailsService.loadUserByUsername 'Cosmin'
        user.username == 'Cosmin'
        user.password == 'thepass'
    }
}