package com.app.clinic.service;

import com.app.clinic.repository.ProfessionalRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfessionalRepository professionalRepository;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CustomUserDetailsService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        logger.info("Loading user by professional");
        return professionalRepository.findOneByEmail(email)
                .map(value -> User
                        .builder()
                        .username(email)
                        .password(value.getPassword())
                        .build())
                .orElse(null);
    }
}
