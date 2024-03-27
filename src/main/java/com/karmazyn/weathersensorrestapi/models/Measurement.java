package com.karmazyn.weathersensorrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private int id;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "raining", nullable = false)
    private boolean raining;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id", referencedColumnName = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}
