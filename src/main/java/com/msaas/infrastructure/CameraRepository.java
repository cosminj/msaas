package com.msaas.infrastructure;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.msaas.model.Camera;
import com.msaas.model.CameraState;

@Repository
public interface CameraRepository extends PagingAndSortingRepository<Camera, Long> {

    List<Camera> findTop4ByState(CameraState state, Sort sort);
}
