package com.example.AcademiApp.repository.direccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AcademiApp.model.Entities.direccion.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
   @Query(value = "SELECT * FROM region WHERE regi_nombre = :nombre AND pais_id = :paisId LIMIT 1", nativeQuery = true)
   Optional<Region> findByNombreAndPais(@Param("nombre") String nombre, @Param("paisId") int paisId);
}
