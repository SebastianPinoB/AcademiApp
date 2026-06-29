package com.example.AcademiApp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.AcademiApp.model.entities.Sala;
import com.example.AcademiApp.repository.SalaRepository;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public Sala crearSala(Sala request) {
        if (salaRepository.findBySalaNombre(request.getSalaNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una sala con el nombre '" + request.getSalaNombre() + "'.");
        }
        Sala sala = new Sala();
        sala.setSalaNombre(request.getSalaNombre());
        sala.setSalaCapacidad(request.getSalaCapacidad());
        return salaRepository.save(sala);
    }

    @Transactional(readOnly = true)
    public List<Sala> obtenerTodas() {
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sala obtenerPorId(int id) {
        return salaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada con ID: " + id));
    }

    @Transactional
    public Sala actualizarSala(int id, Sala request) {
        Sala sala = salaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada con ID: " + id));

        if (!sala.getSalaNombre().equalsIgnoreCase(request.getSalaNombre()) &&
            salaRepository.findBySalaNombre(request.getSalaNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una sala con el nombre '" + request.getSalaNombre() + "'.");
        }

        sala.setSalaNombre(request.getSalaNombre());
        sala.setSalaCapacidad(request.getSalaCapacidad());
        return salaRepository.save(sala);
    }

    @Transactional
    public void eliminarSala(int id) {
        if (!salaRepository.existsById(id)) {
            throw new IllegalArgumentException("Sala no encontrada con ID: " + id);
        }
        salaRepository.deleteById(id);
    }
}