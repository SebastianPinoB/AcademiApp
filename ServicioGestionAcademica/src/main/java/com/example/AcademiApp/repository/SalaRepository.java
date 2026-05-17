package com.example.AcademiApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.entities.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
   // Busca una sala por su nombre
   Optional<Sala> findBySalaNombre(String salaNombre);
}
