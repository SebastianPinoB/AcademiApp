package com.example.AcademiApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.entities.Nivel;

public interface NivelRepository extends JpaRepository<Nivel, Integer> {
   // Busca un nivel por su nombre
   Optional<Nivel> findByNivelNombre(String nivelNombre);
}
