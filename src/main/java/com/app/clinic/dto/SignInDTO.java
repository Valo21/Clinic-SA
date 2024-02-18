package com.app.clinic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
