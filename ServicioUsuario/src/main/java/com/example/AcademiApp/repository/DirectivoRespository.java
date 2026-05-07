package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Directivo;

public interface DirectivoRespository extends JpaRepository<Directivo, Integer> {
   // Buscar si existe un directivo específico por su RUN
   @Query("SELECT COUNT(dir) > 0 FROM Directivo dir WHERE dir.numrun = :numrun")
   boolean existsByNumrun(@Param("numrun") int numrun);
}
