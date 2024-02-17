package com.app.clinic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientHistoryCreationDTO {
    @NotNull
    private String professionalId;
    @NotNull
    private String patientId;
    @NotNull
    private String name;
    @NotNull
    private Integer age;
    @NotNull
    private String sex;
    @NotNull
    private String nationality;
    @NotNull
    private String occupation;
    @NotNull
    private Date date;
    @NotNull
    private String motivation;
    @NotNull
    private String currentDisease;
    @NotNull
    private String currentDiseaseBackground;
    @NotNull
    private String personalBackground;
    @NotNull
    private String familyBackground;
    @NotNull
    private String hereditaryBackground;
}
