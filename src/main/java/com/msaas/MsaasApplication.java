package com.msaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cj
 * @since 30/12/14
 */
@EnableJpaRepositories
@EnableTransactionManagement
@Import(RepositoryRestMvcConfiguration.class)
@SpringBootApplication
public class MsaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaasApplication.class, args);
    }
}