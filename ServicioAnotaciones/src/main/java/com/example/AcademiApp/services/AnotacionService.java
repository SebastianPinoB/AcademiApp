package com.example.AcademiApp.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.models.dto.AnotacionDTO;
import com.example.AcademiApp.models.entities.Anotacion;
import com.example.AcademiApp.models.request.AnotacionRequest;
import com.example.AcademiApp.repository.AnotacionRepository;

@Service
public class AnotacionService {

    @Autowired
    private AnotacionRepository repository;

    public AnotacionDTO registrarAnotacion(AnotacionRequest request) {
        
        // --- COMENTARIO DE CONEXIÓN: SERVICIO AUTH ---
        // 1. Validar que 'request.getIdDocente()' existe y tiene permisos de "Docente" o "Inspector" [3, 6].
        // 2. Validar que 'request.getIdEstudiante()' existe y tiene matrícula vigente en el periodo [3].
        // 3. (Repo Actualizado): Solicitar al Auth el nombre del docente para incluirlo en la notificación.

        // --- COMENTARIO DE CONEXIÓN: SERVICIO VIDA ESTUDIANTIL ---
        // Según RF-55 y CU-AC-01 [3, 6], esta anotación debe "copiarse automáticamente" 
        // o ser vinculada a la Hoja de Vida Integral del estudiante.

        Anotacion nueva = new Anotacion();
        nueva.setAnotDesc(request.getDescripcion());
        nueva.setTipo(request.getTipo());
        nueva.setFecha(LocalDate.now());
        nueva.setHora(LocalTime.now());
        nueva.setIdEstudiante(request.getIdEstudiante());
        nueva.setIdDocente(request.getIdDocente());

        Anotacion guardada = repository.save(nueva);

        // --- COMENTARIO DE CONEXIÓN: SERVICIO MENSAJERÍA/NOTIFICACIONES ---
        // Al guardar con éxito, el sistema debe disparar un correo o notificación push 
        // automática al Apoderado vinculado (RF-48, CU-AC-01 paso 11) [3].

        return new AnotacionDTO(
            guardada.getId(), 
            guardada.getTipo(), 
            guardada.getAnotDesc(), 
            guardada.getFecha(), 
            "Docente ID: " + guardada.getIdDocente() // Nombre real vendría de Auth
        );
    }
}