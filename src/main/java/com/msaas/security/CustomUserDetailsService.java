package com.msaas.security;

import java.util.Collections;
import java.util.Optional;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msaas.infrastructure.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String ROLE = "ROLE_";
    @Resource
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.msaas.model.User> userOpt = userRepo.findByName(username);
        if(userOpt.isPresent()) {
            com.msaas.model.User user = userOpt.get();
            return new User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE + user.getRole().toString())));
        }
        throw new UsernameNotFoundException("Specified username does not exist");
    }
}