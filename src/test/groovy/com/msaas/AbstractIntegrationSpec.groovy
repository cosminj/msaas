package com.msaas

import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@IntegrationTest("server.port:0")
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = MSaas)
abstract class AbstractIntegrationSpec extends Specification {

}