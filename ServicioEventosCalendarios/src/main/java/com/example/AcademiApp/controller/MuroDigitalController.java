package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.MuroDigital;
import com.example.AcademiApp.model.request.MuroDigitalRequest;
import com.example.AcademiApp.service.GestionAcademicaClientService;
import com.example.AcademiApp.service.MuroDigitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("muro-digital")
public class MuroDigitalController {

    @Autowired
    private MuroDigitalService muroDigitalService;

    @Autowired
    private GestionAcademicaClientService gestionAcademicaClientService;

    @GetMapping("/asignaturas-disponibles")
    public ResponseEntity<?> obtenerAsignaturas() {
        try {
            return ResponseEntity.ok(gestionAcademicaClientService.obtenerAsignaturas());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> publicar(@Valid @RequestBody MuroDigitalRequest request) {
        try {
            MuroDigital nueva = muroDigitalService.publicar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MuroDigital>> listar() {
        return ResponseEntity.ok(muroDigitalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(muroDigitalService.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/asignatura/{asignaturaId}")
    public ResponseEntity<List<MuroDigital>> listarPorAsignatura(@PathVariable int asignaturaId) {
        return ResponseEntity.ok(muroDigitalService.listarPorAsignatura(asignaturaId));
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<MuroDigital>> listarPorDocente(@PathVariable int docenteId) {
        return ResponseEntity.ok(muroDigitalService.listarPorDocente(docenteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id,
                                        @Valid @RequestBody MuroDigitalRequest request) {
        try {
            return ResponseEntity.ok(muroDigitalService.actualizar(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            muroDigitalService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}