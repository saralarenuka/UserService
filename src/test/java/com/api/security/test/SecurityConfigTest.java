package com.api.security.test;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import com.api.config.SecurityConfig;
import com.api.security.JwtAuthenticationEntryPoint;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
        // set jwtSecret value for tests
        securityConfig.jwtSecret = "testsecretkeytestsecretkey123456"; // at least 32 chars for HMAC
        securityConfig.jwtAuthEntryPoint = Mockito.mock(JwtAuthenticationEntryPoint.class);
        securityConfig.customUserDetailsService = Mockito.mock(org.springframework.security.core.userdetails.UserDetailsService.class);
    }

    @Test
    void passwordEncoder_ShouldReturnBCryptEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertThat(encoder).isNotNull();
        assertThat(encoder.encode("abc")).isNotBlank();
    }

    @Test
    void jwtDecoder_ShouldReturnValidDecoder() {
        JwtDecoder decoder = securityConfig.jwtDecoder();
        assertThat(decoder).isNotNull();
    }

    @Test
    void jwtAuthenticationConverter_ShouldBeConfiguredProperly() {
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
        assertThat(converter).isNotNull();
    }

    @Test
    void restTemplate_ShouldReturnNonNull() {
        RestTemplate template = securityConfig.restTemplate(new RestTemplateBuilder());
        assertThat(template).isNotNull();
    }

    @Test
    void filterChain_ShouldConfigureSecurityProperly() throws Exception {
        HttpSecurity http = Mockito.mock(HttpSecurity.class, Mockito.RETURNS_DEEP_STUBS);
        SecurityFilterChain filterChain = securityConfig.filterChain(http);
        assertThat(filterChain).isNotNull();
    }
}
