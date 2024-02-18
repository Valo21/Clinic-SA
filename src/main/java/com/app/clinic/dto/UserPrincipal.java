package com.app.clinic.dto;
import com.app.clinic.model.Professional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class UserPrincipal implements UserDetails {
    private final String id;
    private final String email;
    private final String password; // This may be null or not used, depending on your use case
    private final Collection<? extends GrantedAuthority> authorities;
    private final String givenName;
    private final String familyName;
    private final String picture;
    private final String AppointmentLinkId;

    public static UserPrincipal create(Professional user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(), // password may be null or not used
                authorities,
                user.getGivenName(),
                user.getFamilyName(),
                user.getPicture(),
                user.getAppointmentLink().getId()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}