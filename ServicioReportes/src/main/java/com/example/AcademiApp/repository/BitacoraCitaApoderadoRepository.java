package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.models.entities.BitacoraCitaApoderado;

@Repository
public interface BitacoraCitaApoderadoRepository extends JpaRepository<BitacoraCitaApoderado, Integer> {
    
    // CORREGIDO: En tu entidad la variable se llama "usuId"
    List<BitacoraCitaApoderado> findByUsuIdOrderByFechaDesc(int usuId);

    // ELIMINADO: findByIdFuncionario(int idFuncionario) porque BitacoraCitaApoderado no tiene esa propiedad.
}