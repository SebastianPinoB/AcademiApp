package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AcademiApp.model.entities.Asignatura;
import com.example.AcademiApp.model.request.AsignaturaRequest;
import com.example.AcademiApp.repository.AsignaturaRepository;

@Service
public class AsignaturaService {

   @Autowired
   private AsignaturaRepository asignaturaRepository;

   @Transactional
   public Asignatura crearAsignatura(AsignaturaRequest request) {

      // Validacion. La asignatura no se puede duplicar
      if (asignaturaRepository.existsByAsigNombre(request.getAsigNombre())) {
         throw new IllegalArgumentException("La asignatura con el nombre '" + request.getAsigNombre() + "' ya existe.");
      }

      // Si no existe, se crea
      Asignatura asignatura = new Asignatura();
      asignatura.setAsigNombre(request.getAsigNombre());
      asignatura.setAsigDesc(request.getAsigDesc());

      return asignaturaRepository.save(asignatura);
   }

   @Transactional(readOnly = true)
   public Asignatura obtenerPorId(int id) {
      return asignaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
   }

   // Listar todas las asignaturas
   @Transactional(readOnly = true)
   public List<Asignatura> obtenerTodas() {
      return asignaturaRepository.findAll();
   }
}
