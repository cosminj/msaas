package com.msaas.infrastructure;

import com.msaas.model.Observer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author cj
 * @since 02/01/15.
 */
@RepositoryRestResource(collectionResourceRel = "observers", path = "observers")
public interface ObserverRepository extends PagingAndSortingRepository<Observer, Long> {
    
    Observer findByName(@Param("name") String name);
    
}
