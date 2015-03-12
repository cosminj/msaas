package com.msaas.security;

import static com.msaas.security.OauthConfiguration.ROLE_OBS;
import static java.util.Arrays.asList;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msaas.infrastructure.ObserverRepository;
import com.msaas.model.Observer;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private ObserverRepository observerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Observer obs = observerRepository.findByName(username);
        return new User(obs.name, obs.password, asList(new SimpleGrantedAuthority("ROLE_" + ROLE_OBS)));
    }
}