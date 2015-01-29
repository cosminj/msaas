package com.msaas.security;

import com.msaas.model.Observer;
import com.msaas.infrastructure.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ObserverRepository observerRepository;

    @Autowired
    public CustomUserDetailsService(ObserverRepository observerRepository) {
        this.observerRepository = observerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Observer obs = observerRepository.findByName(username);
        return new User(obs.name, obs.password, getAuthorities(asList("OBSERVER")));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return getGrantedAuthorities(roles);
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}