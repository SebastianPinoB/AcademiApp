package com.example.AcademiApp.repository.direccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.direccion.Comuna;

public interface ComunaRepository extends JpaRepository<Comuna, Integer>{
   @Query(value = "SELECT * FROM comuna WHERE comu_nombre = :nombre AND ciudad_id = :ciudadId LIMIT 1", nativeQuery = true)
   Optional<Comuna> findByNombreAndCiudad(@Param("nombre") String nombre, @Param("ciudadId") int ciudadId);
}
