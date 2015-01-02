package com.msaas.security;

import com.msaas.customer.Customer;
import com.msaas.customer.CustomerRepository;
import com.msaas.observer.Observer;
import com.msaas.observer.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author cj
 * @since 30/12/14
 */
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
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}