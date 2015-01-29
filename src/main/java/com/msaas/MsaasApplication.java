package com.msaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cj
 * @since 30/12/14
 */
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class MsaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaasApplication.class, args);
    }
}