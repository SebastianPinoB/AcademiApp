package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.AntecedenteApoderado;
import com.example.AcademiApp.model.request.AntecedenteApoderadoRequest;
import com.example.AcademiApp.service.AntecedenteApoderadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("hojas-vida/{hojaId}/antecedentes-apoderado")
public class AntecedenteApoderadoController {

    @Autowired
    private AntecedenteApoderadoService antecedenteApoderadoService;

    @PostMapping
    public ResponseEntity<?> agregar(@PathVariable int hojaId,
                                     @Valid @RequestBody AntecedenteApoderadoRequest request) {
        try {
            AntecedenteApoderado nuevo = antecedenteApoderadoService.agregar(hojaId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AntecedenteApoderado>> listar(@PathVariable int hojaId) {
        return ResponseEntity.ok(antecedenteApoderadoService.listarPorHoja(hojaId));
    }

    @PutMapping("/{antApoId}")
    public ResponseEntity<?> actualizar(@PathVariable int hojaId,
                                        @PathVariable int antApoId,
                                        @Valid @RequestBody AntecedenteApoderadoRequest request) {
        try {
            AntecedenteApoderado actualizado = antecedenteApoderadoService.actualizar(antApoId, request);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{antApoId}")
    public ResponseEntity<?> eliminar(@PathVariable int hojaId,
                                      @PathVariable int antApoId) {
        try {
            antecedenteApoderadoService.eliminar(antApoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}