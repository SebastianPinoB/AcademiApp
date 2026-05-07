package com.example.AcademiApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Apoderado;

public interface ApoderadoRespository extends JpaRepository<Apoderado, Integer> {
   @Query("SELECT a FROM Apoderado a WHERE a.numrun = :numrun")
   Optional<Apoderado> findByNumrun(@Param("numrun") int numrun);
   

}
