package com.app.clinic.config;

import com.app.clinic.service.AuthenticationService;
import com.app.clinic.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    CustomAuthenticationProvider(@Lazy PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // Perform your custom authentication logic here
        // Retrieve user details from userDetailsService and validate the credentials
        // You can throw AuthenticationException if authentication fails
        // Example: retrieving user details by username from UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Example: validating credentials
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }
        // Create a fully authenticated Authentication object
        Authentication authenticated = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        return authenticated;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        // Return true if this AuthenticationProvider supports the provided authentication class
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}