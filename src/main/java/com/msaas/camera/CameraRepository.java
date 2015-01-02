package com.msaas.camera;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author cj
 * @since 31/12/14.
 */
@RepositoryRestResource(collectionResourceRel = "cameras", path = "cameras")
public interface CameraRepository extends PagingAndSortingRepository<Camera, Long> {

}
