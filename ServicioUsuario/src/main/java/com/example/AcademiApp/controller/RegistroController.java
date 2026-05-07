package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.request.RegistroDirectivoRequest;
import com.example.AcademiApp.model.request.RegistroDocenteRequest;
import com.example.AcademiApp.model.request.RegistroEstudianteRequest;
import com.example.AcademiApp.model.request.RegistroInspectorRequest;
import com.example.AcademiApp.model.request.RegistroRequest;
import com.example.AcademiApp.service.RegistroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/registro")
public class RegistroController {

   @Autowired
   private RegistroService registroService;

   // REGISTRO
   @PostMapping("")
   public ResponseEntity<String> registrar(@RequestBody RegistroRequest nuevoRegistro) {
      registroService.registrarAlumno(nuevoRegistro);
      return ResponseEntity.ok("Registro de apoderado y alumno exitoso.");
   }

   // ENDPOINTS ALUMNOS
   @GetMapping("/alumnos")
   public ResponseEntity<List<Estudiante>> listarAlumnos() {
      return ResponseEntity.ok(registroService.obtenerTodosEstudiantes());
   }

   @GetMapping("/alumno/{id}")
   public ResponseEntity<Estudiante> obtenerAlumno(@PathVariable int id) {
      return ResponseEntity.ok(registroService.obtenerEstudiante(id));
   }

   @GetMapping("/apoderados")
   public ResponseEntity<List<Apoderado>> listarApoderados() {
      return ResponseEntity.ok(registroService.obtenerTodosApoderados());
   }

   @GetMapping("/apoderado/{id}")
   public ResponseEntity<Apoderado> obtenerApoderado(@PathVariable int id) {
      return ResponseEntity.ok(registroService.buscarApoderado(id));
   }

   // Editar Estudiante
   @PutMapping("/alumno/{id}")
   public ResponseEntity<String> editarAlumno(@PathVariable int id, @RequestBody RegistroEstudianteRequest request) {
      registroService.actualizarEstudiante(id, request);
      return ResponseEntity.ok("Estudiante actualizado con éxito");
   }

   @PutMapping("/apoderado/{id}")
   public ResponseEntity<String> editarApoderado(@PathVariable int id, @RequestBody RegistroEstudianteRequest request) {
      registroService.actualizarApoderado(id, request);
      return ResponseEntity.ok("Apoderado actualizado con éxito");
   }

   @DeleteMapping("/alumno/{id}")
   public ResponseEntity<String> eliminarAlumno(@PathVariable int id) {
      registroService.eliminarEstudiante(id);
      return ResponseEntity.ok("Alumno eliminado (el apoderado permanece en el sistema).");
   }

   // Considerar lo que dice tu compare
   // Normalmente, en los métodos PUT (editar),
   // no se recomienda permitir el cambio de contraseña
   // (usu_pass) en el mismo formulario que el nombre o el email.
   // Es mejor tener un método aparte de
   // "Cambiar contraseña" por seguridad.
   // Si te fijas, en el código de arriba omití el setUsu_pass
   // para evitar accidentes.

   // --------------- FUNCIONARIOS

   // Endpoint para Docentes
   @PostMapping("/funcionario/docente")
   public ResponseEntity<String> registrarDocente(@RequestBody RegistroDocenteRequest req) {
      registroService.registrarDocente(req);
      return ResponseEntity.ok("Docente registrado correctamente");
   }

   // Endpoint para Inspectores
   @PostMapping("/funcionario/inspector")
   public ResponseEntity<String> registrarInspector(@RequestBody RegistroInspectorRequest req) {
      registroService.registrarInspector(req);
      return ResponseEntity.ok("Inspector registrado correctamente");
   }

   // Endpoint para Directivos
   @PostMapping("/funcionario/directivo")
   public ResponseEntity<String> registrarDirectivo(@RequestBody RegistroDirectivoRequest req) {
      registroService.registrarDirectivo(req);
      return ResponseEntity.ok("Directivo registrado correctamente");
   }
}
