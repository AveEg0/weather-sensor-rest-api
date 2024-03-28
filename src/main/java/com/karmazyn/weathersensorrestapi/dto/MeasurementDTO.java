package com.karmazyn.weathersensorrestapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MeasurementDTO {

    @NotNull
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensor;
}
