package com.karmazyn.weathersensorrestapi.services;
import com.karmazyn.weathersensorrestapi.models.Sensor;
import com.karmazyn.weathersensorrestapi.repositories.SensorsRepository;
import com.karmazyn.weathersensorrestapi.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundPerson = sensorsRepository.findById(id);

        return foundPerson.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findOne(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void save (Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor updatedSensor) {
        updatedSensor.setId(id);
        sensorsRepository.save(updatedSensor);
    }

    @Transactional
    public void delete(int id) {
        sensorsRepository.deleteById(id);
    }

}
