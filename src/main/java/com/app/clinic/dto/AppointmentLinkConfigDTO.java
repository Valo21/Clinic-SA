package com.app.clinic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AppointmentLinkConfigDTO {
    @NotNull
    private Integer duration;
    @NotNull
    private String professionalId;
}
