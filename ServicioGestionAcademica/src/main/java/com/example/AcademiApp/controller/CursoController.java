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

import com.example.AcademiApp.model.entities.Curso;
import com.example.AcademiApp.model.request.CursoRequest;
import com.example.AcademiApp.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

   @Autowired
   private CursoService cursoService;

   @PostMapping
   public ResponseEntity<Curso> crearCurso(@RequestBody CursoRequest request) {
      Curso nuevoCurso = cursoService.registrarCurso(request);
      return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
   }

   @GetMapping
   public ResponseEntity<List<Curso>> listarCursos() {
      List<Curso> cursos = cursoService.obtenerTodos();
      return ResponseEntity.ok(cursos);
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> buscarPorId(@PathVariable int id) {
      try {
         Curso curso = cursoService.obtenerPorId(id);
         return ResponseEntity.ok(curso);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> actualizarCurso(@PathVariable int id, @RequestBody CursoRequest request) {
      try {
         Curso cursoActualizado = cursoService.actualizarCurso(id, request);
         return ResponseEntity.ok(cursoActualizado);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> eliminarCurso(@PathVariable int id) {
      try {
         cursoService.eliminarCurso(id);
         return ResponseEntity.ok("Curso eliminado exitosamente con ID: " + id);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }

}
