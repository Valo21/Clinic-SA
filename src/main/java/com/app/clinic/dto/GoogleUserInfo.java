package com.app.clinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class GoogleUserInfo {
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
}