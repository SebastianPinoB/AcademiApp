package com.example.AcademiApp.controller;

import com.example.AcademiApp.model.entities.AntecedenteMedico;
import com.example.AcademiApp.model.request.AntecedenteMedicoRequest;
import com.example.AcademiApp.service.AntecedenteMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hojas-vida/{hojaId}/antecedente-medico")
public class AntecedenteMedicoController {

    @Autowired
    private AntecedenteMedicoService antecedenteMedicoService;

    @PutMapping
    public ResponseEntity<?> guardar(@PathVariable int hojaId,
                                     @Valid @RequestBody AntecedenteMedicoRequest request) {
        try {
            AntecedenteMedico resultado = antecedenteMedicoService.guardar(hojaId, request);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtener(@PathVariable int hojaId) {
        try {
            AntecedenteMedico antecedente = antecedenteMedicoService.obtenerPorHoja(hojaId);
            return ResponseEntity.ok(antecedente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}