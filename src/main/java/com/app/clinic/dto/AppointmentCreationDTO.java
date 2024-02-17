package com.app.clinic.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class AppointmentCreationDTO {
    private String personalIDType;
    private String personalID;
    private String firstName;
    private String lastName;
    private String linkId;
    private String description;
    private Date date;
}
