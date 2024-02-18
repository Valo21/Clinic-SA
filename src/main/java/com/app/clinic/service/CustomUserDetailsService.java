package com.app.clinic.service;

import com.app.clinic.dto.UserPrincipal;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.ProfessionalRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Optional<Professional> professional = professionalRepository.findOneByEmail(email);
        return professional.map(value -> (UserDetails) UserPrincipal.create(value, null)).orElse(null);
    }
}
