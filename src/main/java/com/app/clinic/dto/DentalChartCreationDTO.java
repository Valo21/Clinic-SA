package com.app.clinic.dto;

import com.app.clinic.model.DentalChartQuadrant;
import lombok.Getter;

import java.util.List;

@Getter
public class DentalChartCreationDTO {
    private List<DentalChartQuadrant> quadrants;
}
