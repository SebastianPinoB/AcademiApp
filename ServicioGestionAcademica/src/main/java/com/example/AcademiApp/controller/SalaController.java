package com.example.AcademiApp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AcademiApp.model.entities.Sala;
import com.example.AcademiApp.service.SalaService;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Sala request) {
        try {
            return new ResponseEntity<>(salaService.crearSala(request), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Sala>> obtenerTodas() {
        return ResponseEntity.ok(salaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(salaService.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Sala request) {
        try {
            return ResponseEntity.ok(salaService.actualizarSala(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            salaService.eliminarSala(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}