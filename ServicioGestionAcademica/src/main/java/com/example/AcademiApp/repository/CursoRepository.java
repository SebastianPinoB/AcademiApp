package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer>{
   
}
