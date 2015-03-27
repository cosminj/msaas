package com.msaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cj
 * @since 30/12/14
 */
@EnableJpaRepositories
@EnableTransactionManagement
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MSaasApp {

    public static void main(String[] args) {
        SpringApplication.run(MSaasApp.class, args);
    }
}