package com.karmazyn.weathersensorrestapi.util;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
