package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.models.dtos.ActaDTO;
import com.example.AcademiApp.models.dtos.CitaDTO;
import com.example.AcademiApp.models.requests.ActaRequest;
import com.example.AcademiApp.models.requests.CitaRequest;
import com.example.AcademiApp.services.ActaService;
import com.example.AcademiApp.services.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bitacoras")
@RequiredArgsConstructor
public class ReporteController {

    private final CitaService citaService;
    private final ActaService actaService;

    // ==========================================
    // ENDPOINTS DE CITAS
    // ==========================================

    // GET - Obtener todas las citas
    @GetMapping("/citas")
    public ResponseEntity<List<CitaDTO>> getCitas() {
        return ResponseEntity.ok(citaService.getAllCitas());
    }

    // POST - Registrar Cita con Apoderado
    @PostMapping("/citas")
    public ResponseEntity<?> registrarCita(@Valid @RequestBody CitaRequest request) {
        try {
            CitaDTO nuevaCita = citaService.registrarCita(request);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Devuelve 400 si el estudiante no existe
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // ==========================================
    // ENDPOINTS DE ACTAS
    // ==========================================

    // GET - Obtener actas de reuniones de apoderados
    @GetMapping("/actas/apoderados")
    public ResponseEntity<List<ActaDTO>> getActasApoderados() {
        return ResponseEntity.ok(actaService.getAllActasApoderados());
    }

    // GET - Obtener actas de reuniones generales (funcionarios)
    @GetMapping("/actas/generales")
    public ResponseEntity<List<ActaDTO>> getActasGenerales() {
        return ResponseEntity.ok(actaService.getAllActasGenerales());
    }

    // POST - Registrar Acta (Reunión Apoderados o General)
    @PostMapping("/actas")
    public ResponseEntity<?> registrarActa(@Valid @RequestBody ActaRequest request) {
        try {
            ActaDTO nuevaActa = actaService.registrarActa(request);
            return new ResponseEntity<>(nuevaActa, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Devuelve 400 si el curso o funcionario no existe
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}