package com.karmazyn.weathersensorrestapi.controllers;

import com.karmazyn.weathersensorrestapi.dto.MeasurementDTO;
import com.karmazyn.weathersensorrestapi.models.Measurement;
import com.karmazyn.weathersensorrestapi.services.MeasurementService;
import com.karmazyn.weathersensorrestapi.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementService measurementService;
    private final MeasurementDTOValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementDTOValidator validator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll().stream().map(this::convertMeasurementToDTO).toList();
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable int id) {
        return convertMeasurementToDTO(measurementService.findOne(id));
    }

    @GetMapping("/rainy_days_count")
    public Map<String, Integer> getRainyDaysCount() {
        Map<String, Integer> rainyDaysCount = new HashMap<>();
        rainyDaysCount.put("rainy days count", measurementService.getRainyDaysCount(true));
        return rainyDaysCount;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        validator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMsg
                    .append(error.getField()).append(" - ")
                    .append(error.getDefaultMessage()).append(";"));
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        measurementService.save(convertDTOToMeasurement(measurementDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    private MeasurementDTO convertMeasurementToDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertDTOToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
