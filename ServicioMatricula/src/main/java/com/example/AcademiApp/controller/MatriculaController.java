package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.models.dto.MatriculaDTO;
import com.example.AcademiApp.models.request.DesvinculacionRequest;
import com.example.AcademiApp.models.request.MatriculaRequest;
import com.example.AcademiApp.services.MatriculaService;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    /**
     */
    @PostMapping
    public ResponseEntity<MatriculaDTO> gestionarMatricula(@RequestBody MatriculaRequest request) {
        MatriculaDTO response = matriculaService.gestionarMatricula(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     */
    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> obtenerTodas() {
        return ResponseEntity.ok(matriculaService.obtenerTodas());
    }

    /**
     */
    @GetMapping("/usuario/{usuId}")
    public ResponseEntity<MatriculaDTO> obtenerPorUsuario(@PathVariable int usuId) {
        return ResponseEntity.ok(matriculaService.obtenerPorUsuario(usuId));
    }

    /**
     */
    @PutMapping("/desvincular")
    public ResponseEntity<String> desvincularEstudiante(@RequestBody DesvinculacionRequest request) {
        String mensaje = matriculaService.desvincularEstudiante(request);
        return ResponseEntity.ok(mensaje);
    }
}