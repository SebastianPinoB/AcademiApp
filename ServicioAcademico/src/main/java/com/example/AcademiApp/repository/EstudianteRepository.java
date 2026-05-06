package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.Entities.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer>{
   
}
