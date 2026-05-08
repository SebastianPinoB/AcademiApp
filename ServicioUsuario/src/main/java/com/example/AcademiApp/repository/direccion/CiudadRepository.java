package com.example.AcademiApp.repository.direccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.direccion.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer>{
   @Query(value = "SELECT * FROM ciudad WHERE ciudad_nombre = :nombre AND regi_id = :regiId LIMIT 1", nativeQuery = true)
   Optional<Ciudad> findByNombreAndRegion(@Param("nombre") String nombre, @Param("regiId") int regiId);
}
