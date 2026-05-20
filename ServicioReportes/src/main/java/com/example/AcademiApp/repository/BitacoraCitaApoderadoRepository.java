package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.models.entities.BitacoraCitaApoderado;

@Repository
public interface BitacoraCitaApoderadoRepository extends JpaRepository<BitacoraCitaApoderado, Integer> {
    List<BitacoraCitaApoderado> findByIdEstudianteOrderByFechaDesc(int idEstudiante);

    List<BitacoraCitaApoderado> findByIdFuncionario(int idFuncionario);

}
