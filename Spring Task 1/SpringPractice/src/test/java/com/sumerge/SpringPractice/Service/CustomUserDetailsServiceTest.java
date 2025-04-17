package com.sumerge.SpringPractice.Service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;




class CustomUserDetailsServiceTest {

    // Dummy PasswordEncoder for testing purposes
    private static class DummyPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return "encoded_" + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encodedPassword.equals(encode(rawPassword));
        }
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        PasswordEncoder encoder = new DummyPasswordEncoder();
        CustomUserDetailsService service = new CustomUserDetailsService(encoder);
        
        UserDetails userDetails = service.loadUserByUsername("zaka");
        
        // Verify username and encoded password
        assertEquals("zaka", userDetails.getUsername());
        assertEquals(encoder.encode("secret"), userDetails.getPassword());
        
        // Check if the user has the role "ROLE_USER"
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        PasswordEncoder encoder = new DummyPasswordEncoder();
        CustomUserDetailsService service = new CustomUserDetailsService(encoder);
        
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("invalidUser")
        );
        assertEquals("User not found", exception.getMessage());
    }
}