package com.karmazyn.weathersensorrestapi.util;

import com.karmazyn.weathersensorrestapi.dto.MeasurementDTO;
import com.karmazyn.weathersensorrestapi.models.Sensor;
import com.karmazyn.weathersensorrestapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementDTOValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTOTarget = (MeasurementDTO) target;

        double value = measurementDTOTarget.getValue();
        if (value < -100.0 || value > 100) {
            errors.rejectValue("value", "", "Temperature should be between -100 and 100 degrees");
        }

        Optional<Sensor> sensor = sensorService.findOne(measurementDTOTarget.getSensor().getName());
        if (sensor.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with name " + measurementDTOTarget.getSensor().getName() + " does not exist");
        }
    }
}
