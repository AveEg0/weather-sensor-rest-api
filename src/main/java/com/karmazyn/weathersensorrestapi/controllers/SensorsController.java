package com.karmazyn.weathersensorrestapi.controllers;

import com.karmazyn.weathersensorrestapi.dto.SensorDTO;
import com.karmazyn.weathersensorrestapi.models.Sensor;
import com.karmazyn.weathersensorrestapi.services.SensorService;
import com.karmazyn.weathersensorrestapi.util.SensorDTOValidator;
import com.karmazyn.weathersensorrestapi.util.SensorErrorResponse;
import com.karmazyn.weathersensorrestapi.util.SensorNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorService;
    private final SensorDTOValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, SensorDTOValidator validator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        validator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMsg
                    .append(error.getField()).append(" - ")
                    .append(error.getDefaultMessage()).append(";"));
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorService.save(sensor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
