package com.app.clinic.controller;

import com.app.clinic.dto.*;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.ProfessionalRepository;
import com.app.clinic.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Map.of;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final ProfessionalRepository professionalRepository;
    private final AuthenticationService authenticationService;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication) {
        logger.info("Getting user");
        if (authentication != null) {
            Object user = authentication.getPrincipal();
            UserInfo userInfo = null;
            if (user instanceof UserPrincipal info) {
                userInfo = new UserInfo(
                        info.getId(),
                        info.getGivenName(),
                        info.getFamilyName(),
                        info.getEmail(),
                        info.getPicture(),
                        info.getAppointmentLinkId()
                );
            } else if (user instanceof OidcUser) {
                OidcUserInfo info = ((OidcUser) user).getUserInfo();
                userInfo = new UserInfo(
                        info.getClaimAsString("id"),
                        info.getGivenName(),
                        info.getFamilyName(),
                        info.getEmail(),
                        info.getPicture(),
                        info.getClaimAsString("appointmentLinkId")
                );
            }
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.ok("");
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseDTO> signIn(@RequestBody SignInDTO body, HttpServletRequest req){
        try {
            SecurityContext context = authenticationService.signIn(body.getEmail(), body.getPassword());
            HttpSession session = req.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            return new ResponseEntity<ResponseDTO>(
                    ResponseDTO.builder()
                            .data("Signin")
                    .build(), HttpStatus.OK
            );
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @PostMapping("signup")
    public ResponseEntity<ResponseDTO> signUp(@RequestBody ProfessionalCreationDTO body, HttpServletRequest req){
        try {
            SecurityContext context = authenticationService.signUp(body.getFirstName(), body.getLastName(), body.getEmail(), body.getPassword());
            HttpSession session = req.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            return new ResponseEntity<ResponseDTO>(
                    ResponseDTO.builder()
                            .data("Signin")
                            .build(),
                    HttpStatus.OK
            );
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message(e.getMessage())
                            .build());
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
