package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
   @Query("SELECT COUNT(e) > 0 FROM Estudiante e WHERE e.numrun = :numrun")
   boolean existsByNumrun(@Param("numrun") int numrun);

   // Solución con @Query: Cambia 'idUsuario' por el nombre real de la clave primaria en tu clase Usuario
   @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.apoderado.usuId = :apoderadoId")
   int countByApoderadoId(@Param("apoderadoId") int apoderadoId);
}