package com.example.AcademiApp.repository.direccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.direccion.Pais;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
   @Query(value = "SELECT * FROM pais WHERE pais_nombre = :nombre LIMIT 1", nativeQuery = true)
   Optional<Pais> findByNombre(@Param("nombre") String nombre);
}
