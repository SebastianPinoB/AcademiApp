package com.example.AcademiApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.AcademiApp.models.dto.AnotacionDTO;
import com.example.AcademiApp.models.entities.Anotacion;
import com.example.AcademiApp.models.request.AnotacionCreateRequest;
import com.example.AcademiApp.models.request.AnotacionUpdateRequest;
import com.example.AcademiApp.repository.AnotacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnotacionService {

    private final AnotacionRepository anotacionRepository;

    // ==========================================
    // 1. GET - Obtener todas las anotaciones
    // ==========================================
    public List<AnotacionDTO> getAllAnotaciones() {
        return anotacionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ==========================================
    // 2. GET - Obtener por ID
    // ==========================================
    public AnotacionDTO getAnotacionById(Long id) {
        Anotacion anotacion = anotacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotación no encontrada con el ID: " + id));
        return convertToDTO(anotacion);
    }

    // ==========================================
    // 3. CREATE (POST) - Crear nueva anotación
    // ==========================================
    public AnotacionDTO createAnotacion(AnotacionCreateRequest request) {
        Anotacion nuevaAnotacion = new Anotacion();
        
        // Ahora usamos los getters generados por @Data
        nuevaAnotacion.setAnotDesc(request.getAnotDesc());
        nuevaAnotacion.setTipo(request.getTipo());
        nuevaAnotacion.setFecha(request.getFecha());
        nuevaAnotacion.setHora(request.getHora());
        nuevaAnotacion.setIdEstudiante(request.getIdEstudiante());
        nuevaAnotacion.setIdDocente(request.getIdDocente());

        Anotacion anotacionGuardada = anotacionRepository.save(nuevaAnotacion);
        
        return convertToDTO(anotacionGuardada);
    }

    // ==========================================
    // 4. UPDATE (PUT) - Actualizar anotación existente
    // ==========================================
    public AnotacionDTO updateAnotacion(Long id, AnotacionUpdateRequest request) {
        Anotacion anotacionExistente = anotacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotación no encontrada con el ID: " + id));

        anotacionExistente.setAnotDesc(request.getAnotDesc());
        
        if (request.getTipo() != null) anotacionExistente.setTipo(request.getTipo());
        if (request.getFecha() != null) anotacionExistente.setFecha(request.getFecha());
        if (request.getHora() != null) anotacionExistente.setHora(request.getHora());
        if (request.getIdEstudiante() != null) anotacionExistente.setIdEstudiante(request.getIdEstudiante());
        if (request.getIdDocente() != null) anotacionExistente.setIdDocente(request.getIdDocente());

        Anotacion anotacionActualizada = anotacionRepository.save(anotacionExistente);

        return convertToDTO(anotacionActualizada);
    }

    // ==========================================
    // MÉTODO AUXILIAR: Mapeo de Entidad a DTO (Record)
    // ==========================================
    private AnotacionDTO convertToDTO(Anotacion anotacion) {
        // Al ser un record, lo instanciamos pasando todos los argumentos al constructor
        return new AnotacionDTO(
                anotacion.getId(),
                anotacion.getAnotDesc(),
                anotacion.getTipo(),
                anotacion.getFecha(),
                anotacion.getHora(),
                anotacion.getIdEstudiante(),
                anotacion.getIdDocente()
        );
    }
}