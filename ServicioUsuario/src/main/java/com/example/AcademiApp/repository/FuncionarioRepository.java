package com.example.AcademiApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
   // Esta consulta revisa la tabla 'funcionario', que incluye a Docentes, Inspectores y Directivos
    @Query("SELECT COUNT(f) > 0 FROM Funcionario f WHERE f.numrun = :numrun")
    boolean existsByNumrun(@Param("numrun") int numrun);
    
    // Opcional: Buscar un funcionario genérico por RUN
    @Query("SELECT f FROM Funcionario f WHERE f.numrun = :numrun")
    Optional<Funcionario> findByNumrun(@Param("numrun") int numrun);
}
