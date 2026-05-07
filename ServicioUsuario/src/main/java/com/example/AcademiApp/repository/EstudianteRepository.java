package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
   @Query("SELECT COUNT(e) > 0 FROM Estudiante e WHERE e.numrun = :numrun")
   boolean existsByNumrun(@Param("numrun") int numrun);
}