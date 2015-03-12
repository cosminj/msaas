package com.msaas

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

class MSaasTest extends AbstractIntegrationSpec {

    @Autowired
    private JdbcTemplate template

    def "context loads"() {
        expect:
        1
    }

    def "test default fixtures - should be at least 6 cameras in the db" () {
        expect:
        template.queryForObject('SELECT count(*) FROM camera', Integer) >= 6
    }



}