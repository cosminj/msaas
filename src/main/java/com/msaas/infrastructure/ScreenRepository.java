package com.msaas.infrastructure;

import com.msaas.model.Screen;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author cj
 * @since 02/01/15.
 */
@RepositoryRestResource(collectionResourceRel = "screens", path = "screens")
public interface ScreenRepository extends PagingAndSortingRepository<Screen, Long> {


}
