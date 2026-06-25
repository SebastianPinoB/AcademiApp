package com.example.AcademiApp.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
    
    // Inyectamos los WebClients tal como los definiste en tu @Configuration
    private final WebClient funcionarioWeb;
    private final WebClient cursoWeb;

    public ActaDTO registrarActa(ActaRequest request) {
        
        // ==========================================
        // LÓGICA: REUNIÓN DE APODERADOS
        // ==========================================
        if (request.getCursoId() != null) {
            
            String infoCursoReal = "Curso Desconocido";
            
            try {
                // Petición al microservicio de Cursos usando Object.class
                Object respuestaCurso = cursoWeb.get()
                        .uri("/{id}", request.getCursoId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
                
                if (respuestaCurso != null) {
                    // 1. Casteamos la respuesta general a un Map
                    @SuppressWarnings("unchecked")
                    Map<String, Object> mapaCurso = (Map<String, Object>) respuestaCurso;
                    
                    // 2. Extraemos la letra del curso
                    String letra = (String) mapaCurso.get("cursoLetra");
                    
                    // 3. El nivel viene como un sub-objeto JSON, lo casteamos a otro Map
                    @SuppressWarnings("unchecked")
                    Map<String, Object> mapaNivel = (Map<String, Object>) mapaCurso.get("nivel");
                    String nombreNivel = mapaNivel != null ? (String) mapaNivel.get("nivelNombre") : "";
                    
                    infoCursoReal = nombreNivel + " " + letra;
                }
            } catch (WebClientResponseException.NotFound e) {
                throw new IllegalArgumentException("No se pudo registrar el acta: El curso con ID " + request.getCursoId() + " no existe.");
            }

            // Guardar en la Base de Datos local del microservicio de Reportes
            BitacoraReuApoderados reuApo = new BitacoraReuApoderados();
            reuApo.setFecha(request.getFecha());
            reuApo.setHora(request.getHora());
            reuApo.setDescripcion(request.getDescripcion());
            reuApo.setTemasTratados(request.getTemasTratados());
            reuApo.setAcuerdos(request.getAcuerdos());
            reuApo.setObservaciones(request.getObservaciones());
            reuApo.setCursoId(request.getCursoId());

            BitacoraReuApoderados guardada = reuApoderadosRepository.save(reuApo);
            
            // Retornar DTO con la información enriquecida extraída del otro servicio
            return new ActaDTO(
                    guardada.getIdBitacoraReuApoderados(),
                    guardada.getFecha(), 
                    guardada.getHora(),
                    guardada.getDescripcion(), 
                    guardada.getTemasTratados(),
                    guardada.getAcuerdos(), 
                    guardada.getObservaciones(),
                    infoCursoReal, 
                    null
            );
        } 
        
        // ==========================================
        // LÓGICA: REUNIÓN GENERAL (FUNCIONARIOS)
        // ==========================================
        else if (request.getIdFuncionario() != null) {
            
            String nombreFuncionarioReal = "Funcionario Desconocido";
            
            try {
                // Petición al microservicio de Usuarios/Registro usando Object.class
                Object respuestaFuncionario = funcionarioWeb.get()
                        .uri("/{id}", request.getIdFuncionario())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
                
                if (respuestaFuncionario != null) {
                    // Casteamos la respuesta a Map para extraer el campo
                    @SuppressWarnings("unchecked")
                    Map<String, Object> mapaFunc = (Map<String, Object>) respuestaFuncionario;
                    
                    // Extraemos directamente la propiedad "nombre"
                    nombreFuncionarioReal = (String) mapaFunc.get("nombre"); 
                }
            } catch (WebClientResponseException.NotFound e) {
                throw new IllegalArgumentException("No se pudo registrar el acta: El funcionario con ID " + request.getIdFuncionario() + " no existe.");
            }

            // Guardar en la Base de Datos local
            BitacoraReuGeneral reuGen = new BitacoraReuGeneral();
            reuGen.setFecha(request.getFecha());
            reuGen.setHora(request.getHora());
            reuGen.setDescripcion(request.getDescripcion());
            reuGen.setTemasTratados(request.getTemasTratados());
            reuGen.setAcuerdos(request.getAcuerdos());
            reuGen.setObservaciones(request.getObservaciones());
            reuGen.setIdFuncionario(request.getIdFuncionario());

            BitacoraReuGeneral guardada = reuGeneralRepository.save(reuGen);

            // Retornar DTO con el nombre real del funcionario
            return new ActaDTO(
                    guardada.getIdReunGeneral(),
                    guardada.getFecha(), 
                    guardada.getHora(),
                    guardada.getDescripcion(), 
                    guardada.getTemasTratados(),
                    guardada.getAcuerdos(), 
                    guardada.getObservaciones(),
                    null,
                    nombreFuncionarioReal 
            );
        } else {
            throw new IllegalArgumentException("Debe indicar al menos un 'cursoId' (Reunión Apoderados) o 'idFuncionario' (Reunión General)");
        }
    }
}