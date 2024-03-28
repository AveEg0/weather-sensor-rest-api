package com.karmazyn.weathersensorrestapi.repositories;

import com.karmazyn.weathersensorrestapi.models.Measurement;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    int countByRaining(boolean raining);

    @Query("SELECT m FROM Measurement m ORDER BY id LIMIT 200")
    List<Measurement> findAllWithLimit();
}
