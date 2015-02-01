package com.msaas.rest;

import static javax.transaction.Transactional.TxType.REQUIRED;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaas.infrastructure.ObserverRepository;
import com.msaas.model.Screen;
import com.msaas.service.ScreenService;

@RestController
public class ObserverController {

    @Resource
    private ObserverRepository observerRepository;
    @Resource
    private ScreenService screenService;

    @RequestMapping("/server/nextScreen")
    @Transactional(value = REQUIRED)
    public Screen nextScreen(@AuthenticationPrincipal User user) {
        return screenService.scrollNextScreen(observerRepository.findByName(user.getUsername()));
    }
}
