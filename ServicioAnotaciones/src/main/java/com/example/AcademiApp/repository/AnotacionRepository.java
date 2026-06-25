package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.models.entities.Anotacion;

public interface AnotacionRepository extends JpaRepository<Anotacion, Long> {
    List<Anotacion> findByIdEstudianteOrderByFechaDesc(int idEstudiante);
}
