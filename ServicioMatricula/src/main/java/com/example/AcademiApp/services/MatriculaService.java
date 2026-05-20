package com.example.AcademiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.models.dto.MatriculaDTO;
import com.example.AcademiApp.models.entities.Matricula;
import com.example.AcademiApp.models.request.DesvinculacionRequest;
import com.example.AcademiApp.models.request.MatriculaRequest;
import com.example.AcademiApp.repository.MatriculaRepository;

@Service
public class MatriculaService {

    @Autowired
    private WebClient usuarioWebClient; // Configurado para http://127.0.0.1:5000//registro/alumno

    @Autowired
    private MatriculaRepository matriculaRepository;

    // Convierte entidad a DTO (Record) de respuesta
    private MatriculaDTO convertirADto(Matricula matricula) {
        return new MatriculaDTO(
            matricula.getId(),
            matricula.getFecha(),
            matricula.isEstVig(),
            matricula.getUsuId()
        );
    }

    /**
     * Gestiona la inscripción o renovación 
     */
    public MatriculaDTO gestionarMatricula(MatriculaRequest nuevo) {
        if (nuevo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de matrícula no proporcionados");
        }

        // 1. Verificar que el Usuario exista en el Microservicio de Usuarios via WebClient
        Object usuarioExiste = null;
        try {
            usuarioExiste = usuarioWebClient.get()
                .uri("/{id}", nuevo.getUsuId())
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe");
                })
                .bodyToMono(Object.class)
                .block();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al verificar el usuario: " + e.getMessage());
        }

        if (usuarioExiste == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario con ID " + nuevo.getUsuId() + " no existe");
        }

        // 2. Lógica de persistencia: Buscar si ya existe para renovar o crear una nueva
        Matricula matricula = matriculaRepository.findByUsuId(nuevo.getUsuId())
                .orElse(new Matricula());

        matricula.setUsuId(nuevo.getUsuId());
        matricula.setFecha(nuevo.getFecha());
        matricula.setEstVig(true); // Se activa al (re)matricular

        return convertirADto(matriculaRepository.save(matricula));
    }

    public List<MatriculaDTO> obtenerTodas() {
        return matriculaRepository.findAll()
            .stream()
            .map(this::convertirADto)
            .toList();
    }

    public MatriculaDTO obtenerPorUsuario(int usuId) {
        return matriculaRepository.findByUsuId(usuId)
            .map(this::convertirADto)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula no encontrada para el usuario"));
    }

    /**
     */
    public String desvincularEstudiante(DesvinculacionRequest request) {
        Matricula matricula = matriculaRepository.findByUsuId(request.getUsuId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe matrícula para desvincular"));

        matricula.setEstVig(false); // Baja lógica
        matriculaRepository.save(matricula);
        
        return "Estudiante desvinculado correctamente el " + request.getFechaDesvinculacion();
    }
}