package com.msaas.infrastructure;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msaas.model.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Customer findByName(@Param("name") String name);
}
