package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.models.dto.AnotacionDTO;
import com.example.AcademiApp.models.request.AnotacionCreateRequest;
import com.example.AcademiApp.models.request.AnotacionUpdateRequest;
import com.example.AcademiApp.services.AnotacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/anotaciones")
@RequiredArgsConstructor
public class AnotacionController {

    private final AnotacionService anotacionService;

    // ==========================================
    // GET - Obtener todas las anotaciones
    // ==========================================
    @GetMapping
    public ResponseEntity<List<AnotacionDTO>> getAllAnotaciones() {
        List<AnotacionDTO> anotaciones = anotacionService.getAllAnotaciones();
        return ResponseEntity.ok(anotaciones);
    }

    // ==========================================
    // GET - Obtener por ID
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<AnotacionDTO> getAnotacionById(@PathVariable Long id) {
        AnotacionDTO anotacion = anotacionService.getAnotacionById(id);
        return ResponseEntity.ok(anotacion);
    }

    // ==========================================
    // POST - Crear nueva anotación
    // ==========================================
    @PostMapping
    public ResponseEntity<AnotacionDTO> createAnotacion(@Valid @RequestBody AnotacionCreateRequest request) {
        AnotacionDTO nuevaAnotacion = anotacionService.createAnotacion(request);
        return new ResponseEntity<>(nuevaAnotacion, HttpStatus.CREATED);
    }

    // ==========================================
    // PUT - Actualizar anotación existente
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<AnotacionDTO> updateAnotacion(
            @PathVariable Long id,
            @Valid @RequestBody AnotacionUpdateRequest request) {
        
        AnotacionDTO anotacionActualizada = anotacionService.updateAnotacion(id, request);
        return ResponseEntity.ok(anotacionActualizada);
    }
}