package com.api.model.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.api.model.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUserConstructorAndGetters() {
        User user = new User(1, "Alice", "alice@example.com");

        assertEquals(1, user.getUid());
        assertEquals("Alice", user.getUname());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setUid(2);
        user.setUname("Bob");
        user.setEmail("bob@example.com");
        user.setRole("ADMIN");
        user.setPhoneNo("9876543210");
        user.setPassword("securePass");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDate(now);
        user.setModifiedDate(now);

        assertEquals(2, user.getUid());
        assertEquals("Bob", user.getUname());
        assertEquals("bob@example.com", user.getEmail());
        assertEquals("ADMIN", user.getRole());
        assertEquals("9876543210", user.getPhoneNo());
        assertEquals("securePass", user.getPassword());
        assertEquals(now, user.getCreatedDate());
        assertEquals(now, user.getModifiedDate());
    }

    @Test
    void testToString() {
        User user = new User(3, "Charlie", "charlie@example.com");
        user.setRole("USER");
        user.setPhoneNo("9876543210");

        String str = user.toString();

        assertTrue(str.contains("Charlie"));
        assertTrue(str.contains("charlie@example.com"));
        assertTrue(str.contains("USER"));
    }

    @Test
    void testInvalidEmailValidation() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setPhoneNo("9876543210"); // valid
        user.setUname("Test");
        user.setPassword("pwd");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void testInvalidPhoneValidation() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setPhoneNo("12345"); // invalid
        user.setUname("Test");
        user.setPassword("pwd");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Phone number must be 10 digits")));
    }

    @Test
    void testBlankEmailValidation() {
        User user = new User();
        user.setEmail(""); // blank
        user.setPhoneNo("9876543210");
        user.setUname("Test");
        user.setPassword("pwd");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email is mandatory")));
    }
}

