package com.app.clinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Oauth2UserInfoDto {
    private String id;
    private String name;
    private String email;
    private String picture;
}
