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

import com.example.AcademiApp.model.entities.BitacoraAsignatura;
import com.example.AcademiApp.model.request.BitacoraRequest;
import com.example.AcademiApp.service.BitacoraAsignaturaService;

@RestController
@RequestMapping("bitacora")
public class BitacoraAsignaturaController {

    @Autowired
    private BitacoraAsignaturaService bitacoraAsignaturaService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody BitacoraRequest request) {
        try {
            BitacoraAsignatura nuevaBitacora = bitacoraAsignaturaService.crearBitacora(request);
            return new ResponseEntity<>(nuevaBitacora, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BitacoraAsignatura>> listarTodas() {
        List<BitacoraAsignatura> bitacoras = bitacoraAsignaturaService.obtenerTodas();
        return ResponseEntity.ok(bitacoras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            BitacoraAsignatura bitacora = bitacoraAsignaturaService.obtenerPorId(id);
            return ResponseEntity.ok(bitacora);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody BitacoraRequest request) {
        try {
            BitacoraAsignatura bitacoraActualizada = bitacoraAsignaturaService.actualizarBitacora(id, request);
            return ResponseEntity.ok(bitacoraActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            bitacoraAsignaturaService.eliminarBitacora(id);
            return ResponseEntity.ok("Bitácora eliminada exitosamente con ID: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
