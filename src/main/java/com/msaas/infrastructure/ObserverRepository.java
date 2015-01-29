package com.msaas.infrastructure;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msaas.model.Observer;

@Repository
public interface ObserverRepository extends PagingAndSortingRepository<Observer, Long> {

    Observer findByName(@Param("name") String name);
}
