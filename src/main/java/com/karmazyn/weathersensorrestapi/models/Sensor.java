package com.karmazyn.weathersensorrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;

@Entity
@Table(name = "sensor")
@Setter @Getter @NoArgsConstructor
@ToString(exclude = "measurements")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private Set<Measurement> measurements;
}
