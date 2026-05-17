package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.model.entities.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer>{
   // Buscar todas las evaluaciones de una asignatura específica
   List<Evaluacion> findByAsignaturaAsigId(int asigId);
}
