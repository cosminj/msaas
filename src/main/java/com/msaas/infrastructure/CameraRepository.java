package com.msaas.infrastructure;

import com.msaas.model.Camera;
import com.msaas.model.CameraState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author cj
 * @since 31/12/14.
 */
@RepositoryRestResource(collectionResourceRel = "cameras", path = "cameras")
public interface CameraRepository extends PagingAndSortingRepository<Camera, Long> {

    List<Camera> findTop4ByState(CameraState state, Sort sort);
}
