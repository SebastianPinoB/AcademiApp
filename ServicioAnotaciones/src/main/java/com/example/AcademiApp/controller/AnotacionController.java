package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.models.dto.AnotacionDTO;
import com.example.AcademiApp.models.entities.Anotacion;
import com.example.AcademiApp.models.request.AnotacionRequest;
import com.example.AcademiApp.repository.AnotacionRepository;
import com.example.AcademiApp.services.AnotacionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/anotaciones")
public class AnotacionController {

    @Autowired
    private AnotacionService service;

    @Autowired
    private AnotacionRepository repository;

    // Registrar nueva anotación (Docente/Inspector)
    @PostMapping
    public ResponseEntity<AnotacionDTO> crear(@RequestBody AnotacionRequest request) {
        // En un entorno real, el API Gateway ya habría validado el rol del usuario [8, 9].
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarAnotacion(request));
    }

    // Consultar historial para el estudiante (Portal de Consultas RF-49)
    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<Anotacion>> consultarHistorial(@PathVariable int id) {
        // --- COMENTARIO DE SEGURIDAD ---
        // Si el usuario es Apoderado, validar en el Servicio Auth que el 'id' 
        // solicitado corresponde a su pupilo registrado.
        return ResponseEntity.ok(repository.findByIdEstudianteOrderByFechaDesc(id));
    }
}