package com.example.AcademiApp.services;

import java.util.Map;

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
    
    // Inyectamos el WebClient apuntando al endpoint de alumnos
    private final WebClient alumnoWeb; 

    public CitaDTO registrarCita(CitaRequest request) {
        
        String nombreEstudianteReal = "Estudiante Desconocido";
        
        try {
            // Petición al microservicio de Usuarios (Endpoint Alumno)
            Object respuestaAlumno = alumnoWeb.get()
                    .uri("/{id}", request.getUsuId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            
            if (respuestaAlumno != null) {
                // 1. Casteamos la respuesta a un Map
                @SuppressWarnings("unchecked")
                Map<String, Object> mapaAlumno = (Map<String, Object>) respuestaAlumno;
                
                // 2. Extraemos el nombre y apellido (basado en tu EstudianteResponse)
                String nombre = (String) mapaAlumno.get("nombre");
                String apellido = (String) mapaAlumno.get("apellidoPaterno");
                
                nombreEstudianteReal = nombre + " " + apellido;
            }
        } catch (WebClientResponseException.NotFound e) {
            // Si el estudiante no existe, detenemos el proceso
            throw new IllegalArgumentException("No se pudo registrar la cita: El estudiante con ID " + request.getUsuId() + " no existe.");
        }

        // Guardar en la Base de Datos local
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

        // Retornar DTO enriquecido
        return new CitaDTO(
                guardada.getIdBitacoraCitaApoderado(),
                guardada.getFecha(),
                guardada.getHora(),
                guardada.getDescripcion(),
                guardada.getTemasTratados(),
                guardada.getAcuerdos(),
                guardada.getObservaciones(),
                nombreEstudianteReal, // Aquí enviamos el dato que fuimos a buscar al otro microservicio
                guardada.isBitFirmaApo(),
                guardada.getFirmaDocente()
        );
    }
}