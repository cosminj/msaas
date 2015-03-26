package com.msaas.security

import com.msaas.AbstractIntegrationSpec
import com.msaas.infrastructure.UserRepository
import com.msaas.model.Role
import com.msaas.model.User
import org.springframework.security.core.userdetails.UserDetails

import javax.annotation.Resource

class CustomerUserDetailsServiceTest extends AbstractIntegrationSpec {

    @Resource
    private CustomUserDetailsService userDetailsService
    @Resource
    private UserRepository userRepo
    private User observer

    def setup() {
        given:
        observer = userRepo.save(new User(name: 'some test observer', password: 'observers password', role: Role.OBSERVER))
    }

    def "should have a next screen"() {
        expect:
        UserDetails user = userDetailsService.loadUserByUsername 'some test observer'
        user.password == 'observers password'
    }
}