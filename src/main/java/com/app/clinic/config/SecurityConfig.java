package com.app.clinic.config;

import com.app.clinic.config.web.CookieCsrfFilter;
import com.app.clinic.service.CustomOidcUserService;
import com.app.clinic.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOidcUserService customOidcUserService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final Environment env;
    private static final List<String> clients = Arrays.asList("google", "facebook");
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private ClientRegistration getRegistration(String client) {
        logger.info("Getting registration");
        String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");
        String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (clientId == null) {
            return null;
        }

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }
        return null;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(this::getRegistration)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "*.ico",
                                "*.css",
                                "*.js",
                                "api/v1/professionals",
                                "api/v1/appointments",
                                "api/v1/appointment-link",
                                "/api/v1/auth")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .oauth2Login(customizer -> customizer
                        .clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientService(authorizedClientService())
                        .loginPage("/#/auth")
                        .userInfoEndpoint(infoEndpoint -> infoEndpoint
                                .oidcUserService(customOidcUserService)
                            )
                        )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }

}
