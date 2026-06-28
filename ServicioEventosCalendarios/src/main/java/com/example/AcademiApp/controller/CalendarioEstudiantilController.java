package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.CalendarioEstudiantil;
import com.example.AcademiApp.model.request.CalendarioEstudiantilRequest;
import com.example.AcademiApp.service.CalendarioEstudiantilService;
import com.example.AcademiApp.service.GestionAcademicaClientService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("calendario")
public class CalendarioEstudiantilController {

    @Autowired
    private CalendarioEstudiantilService calendarioService;

    @Autowired
    private GestionAcademicaClientService gestionAcademicaClientService;

    @GetMapping("/cursos-disponibles")
    public ResponseEntity<?> obtenerCursos() {
        try {
            return ResponseEntity.ok(gestionAcademicaClientService.obtenerCursos());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/asignaturas-disponibles")
    public ResponseEntity<?> obtenerAsignaturas() {
        try {
            return ResponseEntity.ok(gestionAcademicaClientService.obtenerAsignaturas());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CalendarioEstudiantilRequest request) {
        try {
            CalendarioEstudiantil nuevo = calendarioService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CalendarioEstudiantil>> listar() {
        return ResponseEntity.ok(calendarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(calendarioService.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<CalendarioEstudiantil>> listarPorCurso(@PathVariable int cursoId) {
        return ResponseEntity.ok(calendarioService.listarPorCurso(cursoId));
    }

    @GetMapping("/asignatura/{asignaturaId}")
    public ResponseEntity<List<CalendarioEstudiantil>> listarPorAsignatura(@PathVariable int asignaturaId) {
        return ResponseEntity.ok(calendarioService.listarPorAsignatura(asignaturaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id,
                                        @Valid @RequestBody CalendarioEstudiantilRequest request) {
        try {
            return ResponseEntity.ok(calendarioService.actualizar(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            calendarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}