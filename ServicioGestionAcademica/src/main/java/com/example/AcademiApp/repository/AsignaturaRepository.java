package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.entities.Asignatura;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> {
   // Si se necesitas buscar por nombre en el futuro:
   // Optional<Asignatura> findByAsigNombre(String asigNombre);

   boolean existsByAsigNombre(String asigNombre);
}
