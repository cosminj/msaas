package com.msaas.infrastructure;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.msaas.model.Screen;

@Repository
public interface ScreenRepository extends PagingAndSortingRepository<Screen, Long> {

}
