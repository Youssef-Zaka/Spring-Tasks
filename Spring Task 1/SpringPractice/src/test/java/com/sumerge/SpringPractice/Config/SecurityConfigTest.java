package com.sumerge.SpringPractice.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.SpringPractice.Model.CourseDto;
import com.sumerge.SpringPractice.Service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON easily

    private MockMvc mockMvc;

    // Define authentication and header post processors
    private RequestPostProcessor authProcessor;
    private RequestPostProcessor headerProcessor;

    @BeforeEach
    void setup() {
        authProcessor = SecurityMockMvcRequestPostProcessors.httpBasic("zaka", "secret");
        headerProcessor = request -> {
            request.addHeader("x-validation-report", "true");
            return request;
        };

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .defaultRequest(get("/")
                        .with(authProcessor)
                        .with(headerProcessor));

        mockMvc = builder.build();
    }

    @Test
    void testPublicGetEndpoint() throws Exception {
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().is5xxServerError());//500 means it passed security filter
    }

    @Test
    void testAuthenticatedPostEndpoint() throws Exception {
        CourseDto courseDto = CourseDto.builder()
                .name("Valid Course")
                .description("Valid Course Description")
                .credit(3)
                .authorId(1L)
                .build();

        mockMvc.perform(post("/api/courses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courseDto)))
                .andExpect(status().isNotFound()); // passed security, but no such id
    }

    @Test
    void testAuthenticatedPutEndpoint() throws Exception {
        CourseDto updatedCourse = CourseDto.builder()
                .name("Updated Course")
                .description("Updated Description")
                .credit(4)
                .authorId(1L)
                .build();

        mockMvc.perform(put("/api/courses/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCourse)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAuthenticatedDeleteEndpoint() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }
}
