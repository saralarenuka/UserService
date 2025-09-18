package com.api.config.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncodingTest {

    @Test
    void testPasswordEncoding_NotEqualToRawPassword() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "alice123";
        String encodedPassword = encoder.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    void testPasswordEncoding_Matches() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "alice123";
        String encodedPassword = encoder.encode(rawPassword);

        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}
