package com.example.AcademiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AcademiApp.models.entities.BitacoraReuGeneral;

@Repository
public interface BitacoraReuGeneralRepository extends JpaRepository<BitacoraReuGeneral, Integer> {
    List<BitacoraReuGeneral> findByIdFuncionario(int idFuncionario);

}
