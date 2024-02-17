package com.app.clinic.controller;

import com.app.clinic.model.Professional;
import com.app.clinic.repository.ProfessionalRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Map.of;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final ProfessionalRepository professionalRepository;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(ClientRegistrationRepository clientRegistrationRepository, ProfessionalRepository professionalRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication, @AuthenticationPrincipal OAuth2User user) {
        logger.info("Getting user");
        if (user == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            Optional<Professional> professional = professionalRepository.findOneByEmail(user.getAttributes().get("email").toString());
            if (professional.isEmpty()) {
                return new ResponseEntity<>("", HttpStatus.OK);
            } else {
                Map<String, Object> attributes = new HashMap<>(user.getAttributes());
                attributes.put("id", professional.get().getId());
                return ResponseEntity.ok().body(attributes);
            }
        }
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        ClientRegistration registration = clientRegistrationRepository.findByRegistrationId("google");

        String issuerUri = registration.getProviderDetails().getIssuerUri();
        String originUrl = request.getHeader(HttpHeaders.ORIGIN);
        Object[] params = {issuerUri, registration.getClientId(), originUrl};


        String logoutUrl = MessageFormat.format("{0}v2/logout?client_id={1}&returnTo={2}", params);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(of("logoutUrl", logoutUrl));
    }

}
