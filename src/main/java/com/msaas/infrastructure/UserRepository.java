package com.msaas.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.msaas.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @PreAuthorize("hasAnyRole()")
    User findByName(String name);
}
