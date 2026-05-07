package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Inspector;

public interface InspectorRespository extends JpaRepository<Inspector, Integer> {
   // Buscar si existe un inspector específico por su RUN
   @Query("SELECT COUNT(i) > 0 FROM Inspector i WHERE i.numrun = :numrun")
   boolean existsByNumrun(@Param("numrun") int numrun);
}
