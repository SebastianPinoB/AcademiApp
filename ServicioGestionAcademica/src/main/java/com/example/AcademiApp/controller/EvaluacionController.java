package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.Evaluacion;
import com.example.AcademiApp.model.request.EvaluacionRequest;
import com.example.AcademiApp.service.EvaluacionService;

@RestController
@RequestMapping("/evaluacion")
public class EvaluacionController {

   @Autowired
   private EvaluacionService evaluacionService;

   @PostMapping
   public ResponseEntity<?> guardar(@RequestBody EvaluacionRequest request) {
      try {
         Evaluacion nuevaEvaluacion = evaluacionService.crearEvaluacion(request);
         return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   // --- GET: Obtener Todas ---
   @GetMapping
   public ResponseEntity<List<Evaluacion>> obtenerTodas() {
      List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();
      return ResponseEntity.ok(evaluaciones);
   }

   // --- GET: Obtener una por ID ---
   @GetMapping("/{id}")
   public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
      try {
         Evaluacion evaluacion = evaluacionService.obtenerPorId(id);
         return ResponseEntity.ok(evaluacion);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }

   // --- PUT: Actualizar ---
   @PutMapping("/{id}")
   public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody EvaluacionRequest request) {
      try {
         Evaluacion evaluacionActualizada = evaluacionService.actualizarEvaluacion(id, request);
         return ResponseEntity.ok(evaluacionActualizada);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   // --- DELETE: Eliminar ---
   @DeleteMapping("/{id}")
   public ResponseEntity<?> eliminar(@PathVariable int id) {
      try {
         evaluacionService.eliminarEvaluacion(id);
         return ResponseEntity.noContent().build(); // Devuelve un estado 204 (Éxito sin cuerpo)
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }
}
