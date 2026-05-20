package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.model.entities.Asignatura;
import com.example.AcademiApp.model.entities.Evaluacion;
import com.example.AcademiApp.model.request.EvaluacionRequest;
import com.example.AcademiApp.repository.AsignaturaRepository;
import com.example.AcademiApp.repository.EvaluacionRepository;

import jakarta.transaction.Transactional;

@Service
public class EvaluacionService {

   @Autowired
   private EvaluacionRepository evaluacionRepository;
   @Autowired
   private AsignaturaRepository asignaturaRepository;

   @Transactional
   public Evaluacion crearEvaluacion(EvaluacionRequest request) {
      // 1. Validar que la asignatura exista
      Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
            .orElseThrow(() -> new IllegalArgumentException(
                  "No se puede crear la evaluación: Asignatura no encontrada con ID: " + request.getAsignaturaId()));

      // 2. Mapear datos
      Evaluacion evaluacion = new Evaluacion();
      evaluacion.setEvaFecha(request.getEvaFecha());
      evaluacion.setEvaTipo(request.getEvaTipo());
      evaluacion.setEvaPuntaje(request.getEvaPuntaje());
      evaluacion.setAsignatura(asignatura); // Se asocia la asignatura real

      return evaluacionRepository.save(evaluacion);
   }

   public List<Evaluacion> obtenerTodas() {
      return evaluacionRepository.findAll();
   }

   public Evaluacion obtenerPorId(int id) {
      return evaluacionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Evaluación no encontrada con ID: " + id));
   }

   @Transactional
   public Evaluacion actualizarEvaluacion(int id, EvaluacionRequest request) {
      // Verificar si la evaluación existe
      Evaluacion evaluacionExistente = evaluacionRepository.findById(id)
            .orElseThrow(
                  () -> new IllegalArgumentException("No se puede actualizar: Evaluación no encontrada con ID: " + id));

      // Verificar si la asignatura nueva/existente es válida
      Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
            .orElseThrow(
                  () -> new IllegalArgumentException("Asignatura no encontrada con ID: " + request.getAsignaturaId()));

      // Modificar campos
      evaluacionExistente.setEvaFecha(request.getEvaFecha());
      evaluacionExistente.setEvaTipo(request.getEvaTipo());
      evaluacionExistente.setEvaPuntaje(request.getEvaPuntaje());
      evaluacionExistente.setAsignatura(asignatura);

      return evaluacionRepository.save(evaluacionExistente);
   }

   @Transactional
   public void eliminarEvaluacion(int id) {
      if (!evaluacionRepository.existsById(id)) {
         throw new IllegalArgumentException("No se puede eliminar: Evaluación no encontrada con ID: " + id);
      }
      evaluacionRepository.deleteById(id);
   }

}
