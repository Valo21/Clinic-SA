package com.app.clinic.service;

import com.app.clinic.controller.AuthController;
import com.app.clinic.model.AppointmentLink;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.AppointmentLinkRepository;
import com.app.clinic.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final ProfessionalRepository professionalRepository;
    private final AppointmentLinkRepository appointmentLinkRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Override
    @SneakyThrows
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        logger.info("Loading oidcUser");
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        try {
            return processOidcUser(oidcUserRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest oidcUserRequest, OidcUser oidcUser) {
        logger.info("Finding user by email...");
        Optional<Professional> userOptional = professionalRepository.findOneByEmail(oidcUser.getEmail());
        return userOptional
                .map(existingUser -> updateExistingUser(existingUser, oidcUser))
                .orElseGet(() -> registerNewUser(oidcUserRequest, oidcUser));
    }

    public static String generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String num = "0123456789";
        String specialChar = "!@#%";
        String combination = upper + upper.toLowerCase() + num + specialChar;
        int len = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(combination.charAt(
                    ThreadLocalRandom.current().nextInt(
                            combination.length()
                    )
            ));
        }
        return sb.toString();
    }

    private OidcUser registerNewUser(OAuth2UserRequest oAuth2UserRequest, OidcUser oidcUser) {
        logger.info("Registering new user...");
        Professional professional = professionalRepository.save(Professional
                .builder()
                .givenName(oidcUser.getGivenName())
                .familyName(oidcUser.getFamilyName())
                .email(oidcUser.getEmail())
                .emailVerified(oidcUser.getEmailVerified())
                .password(BCrypt.hashpw(generatePassword(), BCrypt.gensalt(10)))
                .picture(oidcUser.getPicture())
                .provider(oAuth2UserRequest.getClientRegistration().getRegistrationId())
                .providerId(oidcUser.getIdToken().toString())
                .build());
        AppointmentLink appointmentLink = appointmentLinkRepository.save(AppointmentLink
                .builder()
                .professional(professional)
                .duration(30)
                .build());
        professional.setAppointmentLink(appointmentLink);
        professionalRepository.save(professional);
        return oidcUser;
    }

    private OidcUser updateExistingUser(Professional existingUser, OidcUser oidcUser) {
        logger.info("Updating user...");
        return oidcUser;
    }
}
