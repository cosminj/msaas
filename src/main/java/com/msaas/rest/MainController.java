package com.msaas.rest;

import static javax.transaction.Transactional.TxType.REQUIRED;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msaas.infrastructure.UserRepository;
import com.msaas.model.Screen;
import com.msaas.service.ScreenService;

@RestController
public class MainController {

    public static final String PATH_USER_DETAILS = "/server/myDetails";
    public static final String PATH_SCROLL_SCREEN = "/server/nextScreen";
    @Resource
    private UserRepository userRepository;

    @Resource
    private ScreenService screenService;

    @RequestMapping(PATH_USER_DETAILS)
    public com.msaas.model.User getMyDetails(@AuthenticationPrincipal User user) {
        return userRepository.findByName(user.getUsername()).get();
    }

    @PreAuthorize("hasRole('OBSERVER')")
    @RequestMapping(PATH_SCROLL_SCREEN)
    @Transactional(value = REQUIRED)
    public Screen nextScreen(@AuthenticationPrincipal User user) {
        return screenService.scrollNextScreen(userRepository.findByName(user.getUsername()).get());
    }
}