package com.example.AcademiApp.service;

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

}
