package com.karmazyn.weathersensorrestapi.util;

import com.karmazyn.weathersensorrestapi.dto.SensorDTO;
import com.karmazyn.weathersensorrestapi.models.Sensor;
import com.karmazyn.weathersensorrestapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorDTOValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTOTarget = (SensorDTO) target;
        Optional<Sensor> sensor = sensorService.findOne(sensorDTOTarget.getName());
        if (sensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with name " + sensorDTOTarget.getName() + " already exists");
        }
    }
}
