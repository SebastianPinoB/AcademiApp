package com.example.AcademiApp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.models.entities.BitacoraReuApoderados;

@Repository
public interface BitacoraReuApoderadosRepository extends JpaRepository<BitacoraReuApoderados, Integer> {
    
    
    List<BitacoraReuApoderados> findByCursoId(int cursoId);
    
    
    List<BitacoraReuApoderados> findByFechaBetween(LocalDate inicio, LocalDate fin);
}