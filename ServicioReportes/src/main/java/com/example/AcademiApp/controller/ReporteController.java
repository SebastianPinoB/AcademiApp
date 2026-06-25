package com.example.AcademiApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // POST - Registrar Cita con Apoderado
    @PostMapping("/citas")
    public ResponseEntity<CitaDTO> registrarCita(@Valid @RequestBody CitaRequest request) {
        CitaDTO nuevaCita = citaService.registrarCita(request);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    // POST - Registrar Acta (Reunión Apoderados o General)
    @PostMapping("/actas")
    public ResponseEntity<ActaDTO> registrarActa(@Valid @RequestBody ActaRequest request) {
        ActaDTO nuevaActa = actaService.registrarActa(request);
        return new ResponseEntity<>(nuevaActa, HttpStatus.CREATED);
    }
}