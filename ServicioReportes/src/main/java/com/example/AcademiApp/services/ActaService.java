package com.example.AcademiApp.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.AcademiApp.models.dtos.ActaDTO;
import com.example.AcademiApp.models.entities.BitacoraReuApoderados;
import com.example.AcademiApp.models.entities.BitacoraReuGeneral;
import com.example.AcademiApp.models.requests.ActaRequest;
import com.example.AcademiApp.repository.BitacoraReuApoderadosRepository;
import com.example.AcademiApp.repository.BitacoraReuGeneralRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActaService {

    private final BitacoraReuApoderadosRepository reuApoderadosRepository;
    private final BitacoraReuGeneralRepository reuGeneralRepository;
    
    // Inyectamos los WebClients
    private final WebClient funcionarioWeb;
    private final WebClient cursoWeb;

    // ==========================================
    // 1. GET - OBTENER TODAS LAS REUNIONES DE APODERADOS
    // ==========================================
    public List<ActaDTO> getAllActasApoderados() {
        return reuApoderadosRepository.findAll().stream().map(acta -> {
            // Buscamos el curso enriquecido por cada registro
            String infoCurso = obtenerInfoCurso(acta.getCursoId());
            
            return new ActaDTO(
                    acta.getIdBitacoraReuApoderados(), acta.getFecha(), acta.getHora(),
                    acta.getDescripcion(), acta.getTemasTratados(), acta.getAcuerdos(),
                    acta.getObservaciones(), infoCurso, null
            );
        }).collect(Collectors.toList());
    }

    // ==========================================
    // 2. GET - OBTENER TODAS LAS REUNIONES GENERALES
    // ==========================================
    public List<ActaDTO> getAllActasGenerales() {
        return reuGeneralRepository.findAll().stream().map(acta -> {
            // Buscamos el nombre del funcionario por cada registro
            String nombreFunc = obtenerNombreFuncionario(acta.getIdFuncionario());
            
            return new ActaDTO(
                    acta.getIdReunGeneral(), acta.getFecha(), acta.getHora(),
                    acta.getDescripcion(), acta.getTemasTratados(), acta.getAcuerdos(),
                    acta.getObservaciones(), null, nombreFunc
            );
        }).collect(Collectors.toList());
    }

    // ==========================================
    // 3. POST - REGISTRAR ACTA (ORQUESTADOR)
    // ==========================================
    public ActaDTO registrarActa(ActaRequest request) {
        
        // --- LÓGICA: REUNIÓN DE APODERADOS ---
        if (request.getCursoId() != null) {
            
            // Validamos que el curso exista antes de guardar
            String infoCursoReal = obtenerInfoCurso(request.getCursoId());
            if (infoCursoReal.equals("Curso Desconocido") || infoCursoReal.equals("Error de Conexión")) {
                 throw new IllegalArgumentException("No se pudo registrar el acta: El curso con ID " + request.getCursoId() + " no existe.");
            }

            BitacoraReuApoderados reuApo = new BitacoraReuApoderados();
            reuApo.setFecha(request.getFecha());
            reuApo.setHora(request.getHora());
            reuApo.setDescripcion(request.getDescripcion());
            reuApo.setTemasTratados(request.getTemasTratados());
            reuApo.setAcuerdos(request.getAcuerdos());
            reuApo.setObservaciones(request.getObservaciones());
            reuApo.setCursoId(request.getCursoId());

            BitacoraReuApoderados guardada = reuApoderadosRepository.save(reuApo);
            
            return new ActaDTO(
                    guardada.getIdBitacoraReuApoderados(), guardada.getFecha(), guardada.getHora(),
                    guardada.getDescripcion(), guardada.getTemasTratados(), guardada.getAcuerdos(), 
                    guardada.getObservaciones(), infoCursoReal, "" // <-- Cambia null por ""
            );
        } 
        
        // --- LÓGICA: REUNIÓN GENERAL (FUNCIONARIOS) ---
        else if (request.getIdFuncionario() != null) {
            
            // Validamos que el funcionario exista antes de guardar
            String nombreFuncionarioReal = obtenerNombreFuncionario(request.getIdFuncionario());
            if (nombreFuncionarioReal.equals("Funcionario Desconocido") || nombreFuncionarioReal.equals("Error de Conexión")) {
                 throw new IllegalArgumentException("No se pudo registrar el acta: El funcionario con ID " + request.getIdFuncionario() + " no existe.");
            }

            BitacoraReuGeneral reuGen = new BitacoraReuGeneral();
            reuGen.setFecha(request.getFecha());
            reuGen.setHora(request.getHora());
            reuGen.setDescripcion(request.getDescripcion());
            reuGen.setTemasTratados(request.getTemasTratados());
            reuGen.setAcuerdos(request.getAcuerdos());
            reuGen.setObservaciones(request.getObservaciones());
            reuGen.setIdFuncionario(request.getIdFuncionario());

            BitacoraReuGeneral guardada = reuGeneralRepository.save(reuGen);

            return new ActaDTO(
                    guardada.getIdReunGeneral(), guardada.getFecha(), guardada.getHora(),
                    guardada.getDescripcion(), guardada.getTemasTratados(), guardada.getAcuerdos(), 
                    guardada.getObservaciones(), "", nombreFuncionarioReal // <-- Cambia null por ""
            );
        } else {
            throw new IllegalArgumentException("Debe indicar al menos un 'cursoId' o un 'idFuncionario'");
        }
    }

    // ==========================================
    // MÉTODOS PRIVADOS DE INTEGRACIÓN HTTP (Helpers)
    // ==========================================

    // ==========================================
    // MÉTODOS PRIVADOS DE INTEGRACIÓN HTTP (AJUSTADOS A TU JSON)
    // ==========================================

    private String obtenerInfoCurso(Integer cursoId) {
        if (cursoId == null) return "Sin Curso";
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapaCurso = cursoWeb.get().uri("/{id}", cursoId)
                    .retrieve().bodyToMono(Map.class).block();
            
            if (mapaCurso != null) {
                // 1. Extraer Nivel (nivel -> nivelNombre)
                String nombreNivel = "Nivel Desconocido";
                if (mapaCurso.get("nivel") instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> mapaNivel = (Map<String, Object>) mapaCurso.get("nivel");
                    nombreNivel = (String) mapaNivel.getOrDefault("nivelNombre", "Nivel Desconocido");
                }

                // 2. Extraer Sala (sala -> salaNombre)
                String nombreSala = "Sala Sin Asignar";
                if (mapaCurso.get("sala") instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> mapaSala = (Map<String, Object>) mapaCurso.get("sala");
                    nombreSala = (String) mapaSala.getOrDefault("salaNombre", "Sala Sin Asignar");
                }

                return nombreNivel + " | " + nombreSala;
            }
        } catch (Exception e) {
            return "Error Conexión Curso";
        }
        return "Curso No Encontrado";
    }

    private String obtenerNombreFuncionario(Integer idFuncionario) {
        if (idFuncionario == null) return "Sin Funcionario";
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapaFunc = funcionarioWeb.get().uri("/{id}", idFuncionario)
                    .retrieve().bodyToMono(Map.class).block();
            
            if (mapaFunc != null) {
                // Aquí está la clave: tu JSON trae "nombreCompleto"
                return (String) mapaFunc.getOrDefault("nombreCompleto", "Nombre Desconocido");
            }
        } catch (Exception e) {
            return "Error Conexión Funcionario";
        }
        return "Funcionario No Encontrado";
    }
}