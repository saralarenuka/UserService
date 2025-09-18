package com.api.config.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.config.SwaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        swaggerConfig = new SwaggerConfig();
    }

    @Test
    void testCustomOpenAPINotNull() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        assertNotNull(openAPI, "OpenAPI bean should not be null");
    }

    @Test
    void testInfoProperties() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Info info = openAPI.getInfo();

        assertNotNull(info);
        assertEquals("User MicroService API", info.getTitle());
        assertEquals("1.0", info.getVersion());
    }

    @Test
    void testSecuritySchemeConfiguration() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI.getComponents());
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("bearerAuth"));

        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");

        assertEquals("bearerAuth", scheme.getName());
        assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
        assertEquals("bearer", scheme.getScheme());
        assertEquals("JWT", scheme.getBearerFormat());
    }

    @Test
    void testSecurityRequirement() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertFalse(openAPI.getSecurity().isEmpty(), "Security requirements should not be empty");

        SecurityRequirement requirement = openAPI.getSecurity().get(0);
        assertTrue(requirement.containsKey("bearerAuth"));
    }
}
