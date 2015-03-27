package com.msaas.rest;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaas.infrastructure.CameraRepository;
import com.msaas.infrastructure.UserRepository;
import com.msaas.model.Camera;
import com.msaas.model.User;

@RestController
public class CustomerController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private CameraRepository cameraRepository;

    @RequestMapping("/server/customerCameras")
    public Set<Camera> customerCameras(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        User user = userRepository.findByName(principal.getUsername());
        return cameraRepository.findByCustomer(user);
    }
}
