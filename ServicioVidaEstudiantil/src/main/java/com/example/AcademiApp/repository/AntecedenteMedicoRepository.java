package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.AntecedenteMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AntecedenteMedicoRepository extends JpaRepository<AntecedenteMedico, Integer> {

    Optional<AntecedenteMedico> findByHojaVida_HojaId(int hojaId);
}