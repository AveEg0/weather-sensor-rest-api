package com.karmazyn.weathersensorrestapi;

import com.karmazyn.weathersensorrestapi.dto.MeasurementDTO;
import com.karmazyn.weathersensorrestapi.models.Measurement;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherSensorRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSensorRestApiApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(MeasurementDTO.class, Measurement.class)
                .addMapping(MeasurementDTO::getSensor, Measurement::setSensor);

        modelMapper.createTypeMap(Measurement.class, MeasurementDTO.class)
                .addMapping(Measurement::getSensor, MeasurementDTO::setSensor);
        return modelMapper;
    }
}
