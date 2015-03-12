package com.msaas.infrastructure;

import static com.msaas.security.OauthConfiguration.ROLE_ADMIN;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.msaas.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    Customer findByName(String name);
}
