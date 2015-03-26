package com.msaas

import org.springframework.jdbc.core.JdbcTemplate

import javax.annotation.Resource

class MSaasTest extends AbstractIntegrationSpec {

    @Resource
    private JdbcTemplate template

    def "context loads"() {
        expect:
        1
    }
}