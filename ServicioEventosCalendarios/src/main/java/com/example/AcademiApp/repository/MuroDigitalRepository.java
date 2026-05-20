package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.MuroDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MuroDigitalRepository extends JpaRepository<MuroDigital, Integer> {
    List<MuroDigital> findByAsignaturaId(int asignaturaId);
    List<MuroDigital> findByDocenteId(int docenteId);
}