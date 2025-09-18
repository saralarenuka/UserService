package com.api.security.test;
import com.api.model.User;
import com.api.repo.UserRepository;
import com.api.security.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        User dbUser = new User();
        dbUser.setEmail("alice@example.com");
        dbUser.setPassword("encodedPass");
        dbUser.setRole("ADMIN");

        when(userRepository.findByEmail("alice@example.com"))
                .thenReturn(Optional.of(dbUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("alice@example.com");

        assertNotNull(userDetails);
        assertEquals("alice@example.com", userDetails.getUsername());
        assertEquals("encodedPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void loadUserByUsername_WhenUserNotFound_ShouldThrowException() {
        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("missing@example.com"));

        verify(userRepository, times(1)).findByEmail("missing@example.com");
    }

    @Test
    void loadUserByUsername_WhenRoleMissing_ShouldReturnEmptyAuthorities() {
        User dbUser = new User();
        dbUser.setEmail("bob@example.com");
        dbUser.setPassword("encodedPass");
        dbUser.setRole(null);

        when(userRepository.findByEmail("bob@example.com"))
                .thenReturn(Optional.of(dbUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("bob@example.com");

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}

