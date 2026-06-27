package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.Mensajeria;
import com.example.AcademiApp.model.request.MensajeColectivoRequest;
import com.example.AcademiApp.model.request.MensajeIndividualRequest;
import com.example.AcademiApp.service.GestionAcademicaClientService;
import com.example.AcademiApp.service.MensajeriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mensajeria")
public class MensajeriaController {

    @Autowired
    private MensajeriaService mensajeriaService;

    @Autowired
    private GestionAcademicaClientService gestionAcademicaClientService;

    @GetMapping("/cursos-disponibles")
    public ResponseEntity<?> obtenerCursosDisponibles() {
        try {
            return ResponseEntity.ok(gestionAcademicaClientService.obtenerCursos());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/individual")
    public ResponseEntity<?> enviarIndividual(@Valid @RequestBody MensajeIndividualRequest request) {
        try {
            Mensajeria nuevo = mensajeriaService.enviarIndividual(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/colectivo")
    public ResponseEntity<?> enviarColectivo(@Valid @RequestBody MensajeColectivoRequest request) {
        try {
            Mensajeria nuevo = mensajeriaService.enviarColectivo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/bandeja/{receptorId}")
    public ResponseEntity<List<Mensajeria>> bandeja(@PathVariable int receptorId) {
        return ResponseEntity.ok(mensajeriaService.bandejEntrada(receptorId));
    }

    @GetMapping("/enviados/{emisorId}")
    public ResponseEntity<List<Mensajeria>> enviados(@PathVariable int emisorId) {
        return ResponseEntity.ok(mensajeriaService.mensajesEnviados(emisorId));
    }

    @GetMapping("/conversacion/{emisorId}/{receptorId}")
    public ResponseEntity<List<Mensajeria>> conversacion(@PathVariable int emisorId,
                                                          @PathVariable int receptorId) {
        return ResponseEntity.ok(mensajeriaService.conversacion(emisorId, receptorId));
    }

    @GetMapping("/colectivo/curso/{cursoId}")
    public ResponseEntity<List<Mensajeria>> colectivosCurso(@PathVariable int cursoId) {
        return ResponseEntity.ok(mensajeriaService.mensajesColectivosCurso(cursoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(mensajeriaService.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<?> marcarLeido(@PathVariable int id) {
        try {
            return ResponseEntity.ok(mensajeriaService.marcarLeido(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            mensajeriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}