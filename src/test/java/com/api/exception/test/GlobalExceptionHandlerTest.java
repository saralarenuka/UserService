package com.api.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.api.exception.GlobalExceptionHandler;
import com.api.exception.UserNameNotFoundException;
import com.api.exception.UserNotFoundException;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserNotFound_ShouldReturn404() {
        UserNotFoundException ex = new UserNotFoundException("User not found with ID: 10");

        ResponseEntity<String> response = handler.handleUserNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found with ID: 10", response.getBody());
    }

    @Test
    void handleUserNameNotFound_ShouldReturn404() {
        UserNameNotFoundException ex = new UserNameNotFoundException("User not found with username: alice");

        ResponseEntity<String> response = handler.handleUserNameNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found with username: alice", response.getBody());
    }
}
