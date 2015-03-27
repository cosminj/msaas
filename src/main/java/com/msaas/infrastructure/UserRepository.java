package com.msaas.infrastructure;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.msaas.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);
}
