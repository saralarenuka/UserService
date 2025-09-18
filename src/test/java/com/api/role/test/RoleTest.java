package com.api.role.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.api.role.Role;

class RoleTest {

    @Test
    void testEnumValuesCount() {
        Role[] roles = Role.values();
        assertEquals(2, roles.length, "Role enum should have 2 constants");
    }

    @Test
    void testEnumValues() {
        assertEquals(Role.USER, Role.valueOf("USER"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
    }

    @Test
    void testEnumNames() {
        Role[] roles = Role.values();
        assertTrue(roles[0].name().equals("USER") || roles[1].name().equals("USER"));
        assertTrue(roles[0].name().equals("ADMIN") || roles[1].name().equals("ADMIN"));
    }

    @Test
    void testEnumToString() {
        assertEquals("USER", Role.USER.toString());
        assertEquals("ADMIN", Role.ADMIN.toString());
    }
}