package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.model.entities.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
   List<Nota> findByEvaluacionEvaId(int evaId);

   List<Nota> findByEstudianteId(int estudianteId);
}