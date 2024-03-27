package com.karmazyn.weathersensorrestapi.repositories;

import com.karmazyn.weathersensorrestapi.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    int countByRaining(boolean raining);
}
