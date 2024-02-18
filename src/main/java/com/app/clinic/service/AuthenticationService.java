package com.app.clinic.service;
import com.app.clinic.model.AppointmentLink;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.AppointmentLinkRepository;
import com.app.clinic.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final ProfessionalRepository professionalRepository;
    private final AppointmentLinkRepository appointmentLinkRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityContext signIn(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        // If authentication is successful, set the authentication in the SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        if (authentication.isAuthenticated()) {
            context.setAuthentication(authentication);
        }
        return context;
    }

    public SecurityContext signUp(String givenName, String familyName, String email, String password) {
        Optional<Professional> optionalProfessional = professionalRepository.findOneByEmail(email);
        if (optionalProfessional.isPresent()){
            throw new AuthenticationException("Email already in use") {};
        }
        Professional professional = professionalRepository.save(
                Professional
                        .builder()
                        .givenName(givenName)
                        .familyName(familyName)
                        .email(email)
                        .emailVerified(false)
                        .password(passwordEncoder.encode(password))
                        .picture("")
                        .build()
        );
        AppointmentLink appointmentLink = appointmentLinkRepository.save(AppointmentLink
                .builder()
                .professional(professional)
                .duration(30)
                .build());
        professional.setAppointmentLink(appointmentLink);
        professionalRepository.save(professional);
        return signIn(email, password);
    }
}