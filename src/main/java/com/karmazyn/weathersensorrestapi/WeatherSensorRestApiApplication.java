package com.karmazyn.weathersensorrestapi;

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
        return new ModelMapper();
    }
}
