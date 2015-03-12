package com.msaas.infrastructure;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.msaas.model.Observer;
import com.msaas.security.OauthConfiguration;

@Repository
public interface ObserverRepository extends PagingAndSortingRepository<Observer, Long> {

    @PreAuthorize("hasRole('" + OauthConfiguration.ROLE_OBS + "')")
    Observer findByName(@Param("name") String name);
}
