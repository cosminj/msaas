package com.msaas.security;

import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msaas.infrastructure.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.msaas.model.User user = userRepo.findByName(username);
        return new User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}