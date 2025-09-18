package com.api.controller.test;
import com.api.controller.AuthController;
import com.api.dto.JwtResponse;
import com.api.dto.LoginRequest;
import com.api.model.User;
import com.api.repo.UserRepository;
import com.api.security.CustomUserDetailsService;
import com.api.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ShouldReturnJwtResponse() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("alice@example.com");
        loginRequest.setPassword("password");

        Authentication mockAuth = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("alice@example.com")
                .password("encodedPass")
                .roles("ADMIN")
                .build();

        when(customUserDetailsService.loadUserByUsername("alice@example.com"))
                .thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails)).thenReturn("fake-jwt-token");

        ResponseEntity<?> response = authController.authenticate(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        JwtResponse body = (JwtResponse) response.getBody();
        assertNotNull(body);
        assertEquals("fake-jwt-token", body.getToken());
        assertEquals("Bearer", body.getTokenType());
    }

    /*@Test
    void register_WhenEmailAlreadyExists_ShouldReturnBadRequest() {
        User user = new User();
        user.setEmail("alice@example.com");
        user.setPassword("1234");

        when(userRepository.findByEmail("alice@example.com"))
                .thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    void register_WhenNewUser_ShouldSaveAndReturnCreated() {
        User user = new User();
        user.setEmail("bob@example.com");
        user.setPassword("1234");
        user.setRole(null);

        when(userRepository.findByEmail("bob@example.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("1234")).thenReturn("encodedPass");

        ResponseEntity<?> response = authController.register(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User created", response.getBody());
        assertEquals("encodedPass", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());

        verify(userRepository, times(1)).save(user);
    }*/
}
