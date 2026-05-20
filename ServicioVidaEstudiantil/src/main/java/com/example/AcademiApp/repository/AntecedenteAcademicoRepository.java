package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.AntecedenteAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AntecedenteAcademicoRepository extends JpaRepository<AntecedenteAcademico, Integer> {

    List<AntecedenteAcademico> findByHojaVida_HojaId(int hojaId);

    boolean existsByHojaVida_HojaIdAndAntAcaAnio(int hojaId, int anio);
}