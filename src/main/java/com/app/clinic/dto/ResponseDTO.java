package com.app.clinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDTO {
    private String message = null;
    private Object data = null;
}
