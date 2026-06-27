package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import com.example.AcademiApp.model.request.HojaVidaRequest;
import com.example.AcademiApp.model.response.HojaVidaResponse;
import com.example.AcademiApp.service.HojaVidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("hojas-vida")
public class HojaVidaController {

    @Autowired
    private HojaVidaService hojaVidaService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody HojaVidaRequest request) {
        try {
            HojaVidaEstudiante nueva = hojaVidaService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<HojaVidaEstudiante>> listar() {
        return ResponseEntity.ok(hojaVidaService.listarTodas());
    }

    @GetMapping("/{hojaId}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int hojaId) {
        try {
            HojaVidaEstudiante hoja = hojaVidaService.obtenerPorId(hojaId);
            HojaVidaResponse response = hojaVidaService.mapearAResponse(hoja);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<?> obtenerPorEstudiante(@PathVariable int estudianteId) {
        try {
            HojaVidaEstudiante hoja = hojaVidaService.obtenerPorEstudiante(estudianteId);
            HojaVidaResponse response = hojaVidaService.mapearAResponse(hoja);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{hojaId}")
    public ResponseEntity<?> eliminar(@PathVariable int hojaId) {
        try {
            hojaVidaService.eliminar(hojaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}