package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.AntecedenteApoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AntecedenteApoderadoRepository extends JpaRepository<AntecedenteApoderado, Integer> {

    List<AntecedenteApoderado> findByHojaVida_HojaId(int hojaId);

    boolean existsByHojaVida_HojaIdAndApoderadoId(int hojaId, int apoderadoId);
}