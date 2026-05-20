package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.CalendarioEstudiantil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalendarioEstudiantilRepository extends JpaRepository<CalendarioEstudiantil, Integer> {
    List<CalendarioEstudiantil> findByCursoId(int cursoId);
    List<CalendarioEstudiantil> findByAsignaturaId(int asignaturaId);
    List<CalendarioEstudiantil> findByDocenteId(int docenteId);
}
