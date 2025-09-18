package com.api.security.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import com.api.security.JwtAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint entryPoint;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthenticationException authException;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        entryPoint = new JwtAuthenticationEntryPoint();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authException = mock(AuthenticationException.class);

        // Mock response writer
        responseWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void commence_ShouldReturnUnauthorizedJson() throws Exception {
        // given
        when(authException.getMessage()).thenReturn("Invalid token");

        // when
        entryPoint.commence(request, response, authException);

        // then
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // flush to capture content
        response.getWriter().flush();

        String expectedJson = "{\"error\":\"Unauthorized\",\"message\":\"Invalid token\"}";
        assertEquals(expectedJson, responseWriter.toString().trim());
    }
}
