package com.msaas.rest;

import static com.msaas.security.CustomUserDetailsService.ROLE;
import static javax.transaction.Transactional.TxType.REQUIRED;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.msaas.infrastructure.UserRepository;
import com.msaas.model.Screen;
import com.msaas.service.ScreenService;

@RestController
public class MainController {

    public static final String PATH_USER_DETAILS = "/server/myDetails";
    public static final String PATH_SCROLL_SCREEN = "/server/nextScreen";
    public static final String PATH_TABS = "/server/tabs";

    public static final Map<String, List<Tab>> tabsPerRoles = ImmutableMap.of(
        ROLE + "ADMIN", ImmutableList.of(new Tab("My Details", "/main"), new Tab("Video", "/video"), new Tab("My Cameras", "/mycameras")),
        ROLE + "CUSTOMER", ImmutableList.of(new Tab("My Details", "/main"), new Tab("My Cameras", "/mycameras")),
        ROLE + "OBSERVER", ImmutableList.of(new Tab("My Details", "/main"), new Tab("Video", "/video"))
    );

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

    @RequestMapping(PATH_TABS)
    public List<Tab> myTabs(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalStateException("Unknown user roles");
        }
        return tabsPerRoles.get(user.getAuthorities().iterator().next().getAuthority());
    }

    @JsonAutoDetect
    private static class Tab {
        private String path;
        private String label;

        public Tab(String label, String path) {
            this.path = path;
            this.label = label;
        }

        public String getPath() {
            return path;
        }

        public String getLabel() {
            return label;
        }
    }
}