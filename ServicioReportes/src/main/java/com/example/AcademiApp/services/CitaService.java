package com.example.AcademiApp.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.AcademiApp.models.dtos.CitaDTO;
import com.example.AcademiApp.models.entities.BitacoraCitaApoderado;
import com.example.AcademiApp.models.requests.CitaRequest;
import com.example.AcademiApp.repository.BitacoraCitaApoderadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final BitacoraCitaApoderadoRepository citaRepository;
    private final WebClient alumnoWeb; 

    // ==========================================
    // 1. GET - OBTENER TODAS LAS CITAS
    // ==========================================
    public List<CitaDTO> getAllCitas() {
        return citaRepository.findAll().stream().map(cita -> {
            // Buscamos el nombre en tiempo real por cada registro
            String nombreEstudiante = obtenerNombreEstudiante(cita.getUsuId());
            
            return new CitaDTO(
                    cita.getIdBitacoraCitaApoderado(),
                    cita.getFecha(),
                    cita.getHora(),
                    cita.getDescripcion(),
                    cita.getTemasTratados(),
                    cita.getAcuerdos(),
                    cita.getObservaciones(),
                    nombreEstudiante, // Nombre real en vez del nulo
                    cita.isBitFirmaApo(),
                    cita.getFirmaDocente()
            );
        }).collect(Collectors.toList());
    }

    // ==========================================
    // 2. POST - REGISTRAR NUEVA CITA
    // ==========================================
    public CitaDTO registrarCita(CitaRequest request) {
        
        // Validar que el estudiante exista antes de guardar
        String nombreEstudianteReal = obtenerNombreEstudiante(request.getUsuId());
        
        if (nombreEstudianteReal.equals("Estudiante Desconocido") || nombreEstudianteReal.equals("Error de Conexión")) {
            throw new IllegalArgumentException("No se pudo registrar la cita: El estudiante con ID " + request.getUsuId() + " no existe.");
        }

        BitacoraCitaApoderado nuevaCita = new BitacoraCitaApoderado();
        nuevaCita.setFecha(request.getFecha());
        nuevaCita.setHora(request.getHora());
        nuevaCita.setDescripcion(request.getDescripcion());
        nuevaCita.setTemasTratados(request.getTemasTratados());
        nuevaCita.setAcuerdos(request.getAcuerdos());
        nuevaCita.setObservaciones(request.getObservaciones());
        nuevaCita.setUsuId(request.getUsuId());
        nuevaCita.setBitFirmaApo(request.getBitFirmaApo() != null ? request.getBitFirmaApo() : false);
        nuevaCita.setFirmaDocente(request.getFirmaDocente());

        BitacoraCitaApoderado guardada = citaRepository.save(nuevaCita);

        return new CitaDTO(
                guardada.getIdBitacoraCitaApoderado(),
                guardada.getFecha(), guardada.getHora(),
                guardada.getDescripcion(), guardada.getTemasTratados(),
                guardada.getAcuerdos(), guardada.getObservaciones(),
                nombreEstudianteReal, 
                guardada.isBitFirmaApo(), guardada.getFirmaDocente()
        );
    }

    // ==========================================
    // MÉTODO PRIVADO DE INTEGRACIÓN HTTP (Helper)
    // ==========================================
    private String obtenerNombreEstudiante(Integer usuId) {
        if (usuId == null) return "Estudiante Desconocido";
        
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapaAlumno = alumnoWeb.get().uri("/{id}", usuId)
                    .retrieve().bodyToMono(Map.class).block();
            
            if (mapaAlumno != null) {
                String nombre = mapaAlumno.get("nombre") != null ? (String) mapaAlumno.get("nombre") : "";
                String apellido = mapaAlumno.get("apellidoPaterno") != null ? " " + mapaAlumno.get("apellidoPaterno") : "";
                
                return (nombre + apellido).trim();
            }
        } catch (WebClientResponseException.NotFound e) {
            return "Estudiante Desconocido";
        } catch (Exception e) {
            return "Error de Conexión";
        }
        return "Estudiante Desconocido";
    }
}