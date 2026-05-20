package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.AntecedenteAcademico;
import com.example.AcademiApp.model.request.AntecedenteAcademicoRequest;
import com.example.AcademiApp.service.AntecedenteAcademicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("hojas-vida/{hojaId}/antecedentes-academicos")
public class AntecedenteAcademicoController {

    @Autowired
    private AntecedenteAcademicoService antecedenteAcademicoService;

    @PostMapping
    public ResponseEntity<?> agregar(@PathVariable int hojaId,
                                     @Valid @RequestBody AntecedenteAcademicoRequest request) {
        try {
            AntecedenteAcademico nuevo = antecedenteAcademicoService.agregar(hojaId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AntecedenteAcademico>> listar(@PathVariable int hojaId) {
        return ResponseEntity.ok(antecedenteAcademicoService.listarPorHoja(hojaId));
    }

    @PutMapping("/{antAcaId}")
    public ResponseEntity<?> actualizar(@PathVariable int hojaId,
                                        @PathVariable int antAcaId,
                                        @Valid @RequestBody AntecedenteAcademicoRequest request) {
        try {
            AntecedenteAcademico actualizado = antecedenteAcademicoService.actualizar(antAcaId, request);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}