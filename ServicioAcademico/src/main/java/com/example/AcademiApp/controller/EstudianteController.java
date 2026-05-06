package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.dto.RegistroAlumnoWrapper;
import com.example.AcademiApp.service.EstudianteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("estudiante")
@RestController
public class EstudianteController {
   @Autowired
   private EstudianteService estudianteService;

   @GetMapping("")
   public List<Estudiante> obtenerTodosUsuarios() {
      return estudianteService.obtenerTodos();
   }

   @GetMapping("/{idEstudiante}")
   public Estudiante buscarPorIdUsuario(@PathVariable Integer idEstudiante) {
      return estudianteService.obtenerPorId(idEstudiante);
   }

   @PostMapping("")
   public ResponseEntity<String> registrar(@RequestBody RegistroAlumnoWrapper request) {
      // Llamamos al service pasando los dos objetos que vienen dentro del wrapper
      estudianteService.registrarEstudianteCompleto(
            request.getNuevoEst(),
            request.getNuevoApo());

      return ResponseEntity.status(HttpStatus.CREATED)
            .body("Estudiante y Apoderado vinculados correctamente");
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> eliminar(@PathVariable int id) {
      // Llamamos al método que ya tenías en el Service
      String mensaje = estudianteService.eliminarEstudiante(id);
      return ResponseEntity.ok(mensaje);
   }

}
