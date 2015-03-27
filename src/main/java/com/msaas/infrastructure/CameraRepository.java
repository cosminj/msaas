package com.msaas.infrastructure;

import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.msaas.model.Camera;
import com.msaas.model.CameraState;
import com.msaas.model.User;

@Repository
public interface CameraRepository extends PagingAndSortingRepository<Camera, Long> {

    @PreAuthorize("hasRole('OBSERVER')")
    Set<Camera> findTop4ByState(CameraState state, Sort sort);

    Set<Camera> findByCustomer(User customer);
}
