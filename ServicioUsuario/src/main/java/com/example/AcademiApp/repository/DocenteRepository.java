package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Integer>{
   // Buscar si existe un docente específico por su RUN
    @Query("SELECT COUNT(d) > 0 FROM Docente d WHERE d.numrun = :numrun")
    boolean existsByNumrun(@Param("numrun") int numrun);
}
