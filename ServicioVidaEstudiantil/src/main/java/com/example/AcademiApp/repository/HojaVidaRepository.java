package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HojaVidaRepository extends JpaRepository<HojaVidaEstudiante, Integer> {

    Optional<HojaVidaEstudiante> findByEstudianteId(int estudianteId);

    boolean existsByEstudianteId(int estudianteId);
}