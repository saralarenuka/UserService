package com.api.security.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.security.JwtUtil;

public class JwtUtilTest {

    public JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
       jwtUtil.secret = "mySecretKeyForJwtMySecretKeyForJwt123"; // must be at least 32 chars
        jwtUtil.jwtExpirationMs = 3600000; // 1 hour
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        UserDetails userDetails = User.withUsername("alice@example.com")
                .password("password")
                .roles("ADMIN")
                .build();

        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtUtil.extractUsername(token);
        assertEquals("alice@example.com", username);
    }

    @Test
    void testValidateToken_Valid() {
        UserDetails userDetails = User.withUsername("bob@example.com")
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

  /*  @Test
    void testValidateToken_Expired() throws InterruptedException {
        // short-lived token
        jwtUtil.jwtExpirationMs = 1; // expire immediately
        UserDetails userDetails = User.withUsername("carol@example.com")
                .password("password")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);

        // wait for expiry
        Thread.sleep(10);

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }*/
}
