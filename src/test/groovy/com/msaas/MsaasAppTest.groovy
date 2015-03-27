package com.msaas

import org.springframework.jdbc.core.JdbcTemplate

import javax.annotation.Resource

class MSaasAppTest extends AbstractIntegrationSpec {

    @Resource
    private JdbcTemplate template

    def "context loads"() {
        expect:
        1
    }
}