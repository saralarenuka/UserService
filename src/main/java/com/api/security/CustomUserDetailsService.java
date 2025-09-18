package com.api.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.model.User;
import com.api.repo.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
/*
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // try email first, then username
        Optional<User> u = userRepository.findByEmail(usernameOrEmail);
        if (!u.isPresent()) {
            u = userRepository.findByUname(usernameOrEmail);
        }
        User user = u.orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));

        // role in DB is already "ROLE_ADMIN" or "ROLE_USER" per your data.sql
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(authority));
    }*/
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Map DB roles to authorities
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRole() != null && !user.getRole().isEmpty()) {
            String role = user.getRole().toUpperCase();
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }
            authorities.add(new SimpleGrantedAuthority(role));
        } else {
            // User has no role, assign empty list
            authorities = Collections.emptyList();
        }

        // Debug
        System.out.println("Loaded authorities for user " + username + ": " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),   // must match username/email passed in authenticate
                user.getPassword(),
                authorities        // can be empty but not null
        );
    }

}
