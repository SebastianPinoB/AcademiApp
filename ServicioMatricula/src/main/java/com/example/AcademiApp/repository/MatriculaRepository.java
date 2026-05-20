package com.example.AcademiApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.models.entities.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer>{
    Optional<Matricula> findByUsuId(Integer usuId);
    boolean existsByUsuId(Integer usuId);  
}
