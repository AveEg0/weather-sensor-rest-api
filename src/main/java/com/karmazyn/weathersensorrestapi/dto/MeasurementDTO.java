package com.karmazyn.weathersensorrestapi.dto;

import com.karmazyn.weathersensorrestapi.models.Sensor;
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
    private Sensor sensor;
}
