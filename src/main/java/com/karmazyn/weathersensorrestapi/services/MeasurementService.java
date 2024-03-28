package com.karmazyn.weathersensorrestapi.services;

import com.karmazyn.weathersensorrestapi.models.Measurement;
import com.karmazyn.weathersensorrestapi.repositories.MeasurementsRepository;
import com.karmazyn.weathersensorrestapi.util.MeasurementNotFoundException;
import com.karmazyn.weathersensorrestapi.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public List<Measurement> findAll(Sort sort) {
        return measurementsRepository.findAll(sort);
    }

    public List<Measurement> findAllWithLimit() {
        return measurementsRepository.findAllWithLimit();
    }

    public Measurement findOne(int id) {
        Optional<Measurement> foundMeasurement = measurementsRepository.findById(id);

        return foundMeasurement.orElseThrow(MeasurementNotFoundException::new);
    }

    public int getRainyDaysCount(boolean raining) {
        return measurementsRepository.countByRaining(raining);
    }

    @Transactional
    public void save (Measurement measurement) {
        measurement.setSensor(
                sensorService.findOne(measurement.getSensor().getName()).orElseThrow(SensorNotFoundException::new));
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    @Transactional
    public void update(int id, Measurement updatedMeasurement) {
        updatedMeasurement.setId(id);
        measurementsRepository.save(updatedMeasurement);
    }

    @Transactional
    public void delete(int id) {
        measurementsRepository.deleteById(id);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreated_at(LocalDateTime.now());
    }
}
