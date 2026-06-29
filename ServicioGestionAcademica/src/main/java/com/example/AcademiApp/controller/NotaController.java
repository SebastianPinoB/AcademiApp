package com.example.AcademiApp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AcademiApp.model.entities.Nota;
import com.example.AcademiApp.model.request.NotaRequest;
import com.example.AcademiApp.service.NotaService;

@RestController
@RequestMapping("/nota")
public class NotaController {

   @Autowired
   private NotaService notaService;

   @PostMapping
   public ResponseEntity<?> crear(@RequestBody NotaRequest request) {
      try {
         Nota nota = notaService.crearNota(request);
         return new ResponseEntity<>(nota, HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   @GetMapping
   public ResponseEntity<List<Nota>> obtenerTodas() {
      return ResponseEntity.ok(notaService.obtenerTodas());
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
      try {
         return ResponseEntity.ok(notaService.obtenerPorId(id));
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }

   @GetMapping("/evaluacion/{evaId}")
   public ResponseEntity<List<Nota>> obtenerPorEvaluacion(@PathVariable int evaId) {
      return ResponseEntity.ok(notaService.obtenerPorEvaluacion(evaId));
   }

   @GetMapping("/estudiante/{estudianteId}")
   public ResponseEntity<List<Nota>> obtenerPorEstudiante(@PathVariable int estudianteId) {
      return ResponseEntity.ok(notaService.obtenerPorEstudiante(estudianteId));
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody NotaRequest request) {
      try {
         return ResponseEntity.ok(notaService.actualizarNota(id, request));
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> eliminar(@PathVariable int id) {
      try {
         notaService.eliminarNota(id);
         return ResponseEntity.noContent().build();
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }
}