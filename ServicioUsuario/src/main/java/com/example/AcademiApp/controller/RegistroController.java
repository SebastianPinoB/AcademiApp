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
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.model.Entities.Directivo;
import com.example.AcademiApp.model.Entities.Docente;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.Entities.Inspector;
import com.example.AcademiApp.model.Entities.Usuario;
import com.example.AcademiApp.model.request.RegistroDirectivoRequest;
import com.example.AcademiApp.model.request.RegistroDocenteRequest;
import com.example.AcademiApp.model.request.RegistroInspectorRequest;
import com.example.AcademiApp.model.request.RegistroRequest;
import com.example.AcademiApp.model.response.EstudianteResponse;
import com.example.AcademiApp.model.response.FuncionarioResponse;
import com.example.AcademiApp.repository.DirectivoRespository;
import com.example.AcademiApp.repository.DocenteRepository;
import com.example.AcademiApp.repository.FuncionarioRepository;
import com.example.AcademiApp.repository.InspectorRespository;
import com.example.AcademiApp.service.RegistroService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/registro")
public class RegistroController {

   @Autowired
   private RegistroService registroService;

   @Autowired
   private DirectivoRespository directivoRespository;

   @Autowired
   private DocenteRepository docenteRepository;

   @Autowired
   private InspectorRespository inspectorRespository;

   @GetMapping("/usuario")
   public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
      List<Usuario> usuarios = registroService.obtenerTodosUsuarios();
      return ResponseEntity.ok(usuarios);
   }
   // ==========================================
   // 1. ENDPOINTS: ALUMNOS Y APODERADOS
   // ==========================================

   @PostMapping("/alumno")
   public ResponseEntity<String> registrarAlumno(@RequestBody RegistroRequest nuevoRegistro) {
      String respuesta = registroService.registrarAlumno(nuevoRegistro);
      return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
   }

   @GetMapping("/alumno")
   public ResponseEntity<List<Estudiante>> obtenerTodosEstudiantes() {
      List<Estudiante> estudiantes = registroService.obtenerTodosEstudiantes();
      return ResponseEntity.ok(estudiantes);
   }

   @GetMapping("/alumno/{id}")
   public ResponseEntity<EstudianteResponse> obtenerEstudiante(@PathVariable int id) {
      EstudianteResponse estudiante = registroService.obtenerEstudiante(id);
      return ResponseEntity.ok(estudiante);
   }

   @PutMapping("/alumno/{id}")
   public ResponseEntity<Void> actualizarAlumnoYApoderado(@PathVariable int id,
         @RequestBody RegistroRequest datosNuevos) {
      registroService.actualizarAlumnoYApoderado(id, datosNuevos);
      return ResponseEntity.noContent().build();
   }

   @DeleteMapping("/alumno/{id}")
   public ResponseEntity<Void> eliminarEstudiante(@PathVariable int id) {
      registroService.eliminarEstudiante(id);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/apoderado")
   public ResponseEntity<List<Apoderado>> obtenerTodosApoderados() {
      List<Apoderado> apoderados = registroService.obtenerTodosApoderados();
      return ResponseEntity.ok(apoderados);
   }

   // ==========================================
   // 2. ENDPOINTS: REGISTRO DE FUNCIONARIOS
   // ==========================================

   @PostMapping("/funcionario/docente")
   public ResponseEntity<Void> registrarDocente(@RequestBody RegistroDocenteRequest req) {
      registroService.registrarDocente(req);
      return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   @PostMapping("/funcionario/inspector")
   public ResponseEntity<Void> registrarInspector(@RequestBody RegistroInspectorRequest req) {
      registroService.registrarInspector(req);
      return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   @PostMapping("/funcionario/directivo")
   public ResponseEntity<Void> registrarDirectivo(@RequestBody RegistroDirectivoRequest req) {
      registroService.registrarDirectivo(req);
      return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   // ==========================================
   // 3. ENDPOINTS: LECTURA DE FUNCIONARIOS (Vistas del Panel)
   // ==========================================

   @GetMapping("/funcionario")
   public ResponseEntity<List<FuncionarioResponse>> obtenerTodosFuncionarios() {
      List<FuncionarioResponse> funcionarios = registroService.obtenerTodosFuncionarios();
      return ResponseEntity.ok(funcionarios);
   }

   @GetMapping("/funcionario/docente")
   public ResponseEntity<List<FuncionarioResponse>> obtenerTodosDocentes() {
      List<FuncionarioResponse> docentes = registroService.obtenerTodosDocentes();
      return ResponseEntity.ok(docentes);
   }

   @GetMapping("/funcionario/inspector")
   public ResponseEntity<List<FuncionarioResponse>> obtenerTodosInspectores() {
      List<FuncionarioResponse> inspectores = registroService.obtenerTodosInspectores();
      return ResponseEntity.ok(inspectores);
   }

   @GetMapping("/funcionario/directivo")
   public ResponseEntity<List<FuncionarioResponse>> obtenerTodosDirectivos() {
      List<FuncionarioResponse> directivos = registroService.obtenerTodosDirectivos();
      return ResponseEntity.ok(directivos);
   }

   // ==========================================
   // OBTENER UN FUNCIONARIO ESPECÍFICO POR ID
   // ==========================================
   @GetMapping("/funcionario/{id}")
   public ResponseEntity<FuncionarioResponse> obtenerFuncionario(@PathVariable int id) {
      FuncionarioResponse funcionario = registroService.obtenerFuncionario(id);
      return ResponseEntity.ok(funcionario);
   }

   // ==========================================
   // 4. ENDPOINTS: EDICIÓN Y ELIMINACIÓN
   // ==========================================

   @PutMapping("/funcionario/docente/{id}")
   public ResponseEntity<Void> actualizarDocente(@PathVariable int id, @RequestBody RegistroDocenteRequest req) {
      registroService.actualizarDocente(id, req);
      return ResponseEntity.noContent().build();
   }

   @PutMapping("/funcionario/inspector/{id}")
   public ResponseEntity<Void> actualizarInspector(@PathVariable int id, @RequestBody RegistroInspectorRequest req) {
      registroService.actualizarInspector(id, req);
      return ResponseEntity.noContent().build();
   }

   @PutMapping("/funcionario/directivo/{id}")
   public ResponseEntity<Void> actualizarDirectivo(@PathVariable int id, @RequestBody RegistroDirectivoRequest req) {
      registroService.actualizarDirectivo(id, req);
      return ResponseEntity.noContent().build();
   }

   @DeleteMapping("/funcionario/{id}")
   public ResponseEntity<Void> eliminarFuncionario(@PathVariable int id) {
      registroService.eliminarFuncionario(id);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/funcionario/docente/{id}")
   public ResponseEntity<?> obtenerDocente(@PathVariable int id) {
      try {
         return ResponseEntity.ok(registroService.obtenerDocente(id));
      } catch (ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
      }
   }

   @GetMapping("/funcionario/inspector/{id}")
   public ResponseEntity<?> obtenerInspector(@PathVariable int id) {
      try {
         return ResponseEntity.ok(registroService.obtenerInspector(id));
      } catch (ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
      }
   }

   @GetMapping("/funcionario/directivo/{id}")
   public ResponseEntity<?> obtenerDirectivo(@PathVariable int id) {
      try {
         return ResponseEntity.ok(registroService.obtenerDirectivo(id));
      } catch (ResponseStatusException e) {
         return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
      }
   }
}