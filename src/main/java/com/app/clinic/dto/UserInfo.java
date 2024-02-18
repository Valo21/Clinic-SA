package com.app.clinic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class UserInfo {
    private final String id;
    private final String givenName;
    private final String familyName;
    private final String email;
    private final String picture;
    private final String appointmentLinkId;
}
