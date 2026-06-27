package com.example.AcademiApp.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.AcademiApp.models.dto.AnotacionDTO;
import com.example.AcademiApp.models.entities.Anotacion;
import com.example.AcademiApp.models.request.AnotacionCreateRequest;
import com.example.AcademiApp.models.request.AnotacionUpdateRequest;
import com.example.AcademiApp.repository.AnotacionRepository;

@Service
public class AnotacionService {

    private final AnotacionRepository anotacionRepository;
    private final WebClient usuarioWeb;
    private final WebClient vidaEstudiantilWeb;
    private final WebClient mensajeriaWeb;

    // El constructor explícito con @Qualifier remueve la ambigüedad en el inicio de la app
    @Autowired
    public AnotacionService(
            AnotacionRepository anotacionRepository,
            @Qualifier("usuarioWeb") WebClient usuarioWeb,
            @Qualifier("vidaEstudiantilWeb") WebClient vidaEstudiantilWeb,
            @Qualifier("mensajeriaWeb") WebClient mensajeriaWeb) {
        
        this.anotacionRepository = anotacionRepository;
        this.usuarioWeb = usuarioWeb;
        this.vidaEstudiantilWeb = vidaEstudiantilWeb;
        this.mensajeriaWeb = mensajeriaWeb;
    }

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
    // 3. CREATE (POST) - Crear nueva anotación (Orquestador)
    // ==========================================
    public AnotacionDTO createAnotacion(AnotacionCreateRequest request) {
        
        // A. Buscar nombres (Ahora lanzarán un error limpio si no existen o fallan)
        String nombreAlumno = obtenerNombreUsuario("/alumno/{id}", request.getIdEstudiante(), "Alumno");
        String nombreDocente = obtenerNombreUsuario("/funcionario/{id}", request.getIdDocente(), "Docente");

        // C. Guardar en Base de Datos local
        Anotacion nuevaAnotacion = new Anotacion();
        nuevaAnotacion.setAnotDesc(request.getAnotDesc());
        nuevaAnotacion.setTipo(request.getTipo());
        nuevaAnotacion.setFecha(request.getFecha());
        nuevaAnotacion.setHora(request.getHora());
        nuevaAnotacion.setIdEstudiante(request.getIdEstudiante());
        nuevaAnotacion.setIdDocente(request.getIdDocente());

        Anotacion anotacionGuardada = anotacionRepository.save(nuevaAnotacion);

        // D. Lógica de Negocio: Notificar al apoderado si la anotación es NEGATIVA
        if ("NEGATIVA".equalsIgnoreCase(request.getTipo())) {
            notificarApoderado(request.getIdEstudiante(), request.getIdDocente(), request.getAnotDesc());
        }
        
        return convertEnrichedToDTO(anotacionGuardada, nombreAlumno, nombreDocente);
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
    // MÉTODOS DE SOPORTE E INTEGRACIÓN HTTP 
    // ==========================================
    
    private String obtenerNombreUsuario(String uri, Integer id, String rol) {
        try {
            Object res = usuarioWeb.get().uri(uri, id).retrieve().bodyToMono(Object.class).block();
            
            if (res instanceof Map) {
                @SuppressWarnings("unchecked") 
                Map<String, Object> map = (Map<String, Object>) res;
                
                // 1. EL ARREGLO: Buscamos primero la llave exacta "nombreCompleto"
                if (map.containsKey("nombreCompleto") && map.get("nombreCompleto") != null) {
                    return (String) map.get("nombreCompleto");
                }
                
                // 2. RESPALDO: Por si el JSON todavía los manda separados en otros endpoints
                String n = extraerTexto(map, "nombre", "usuNombre", "usu_nombre");
                String a = extraerTexto(map, "apellidoPaterno", "usuAppaterno", "usu_appaterno", "apellido");
                
                if (n != null) {
                    return n + (a != null && !a.isEmpty() ? " " + a : "");
                }

                // 3. Si llega aquí, es porque la info viene incompleta
                throw new IllegalArgumentException("El " + rol + " existe, pero su información de usuario está incompleta.");
            }
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("No se puede crear la anotación: El " + rol + " con ID " + id + " no existe.");
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzamos errores controlados
        } catch (Exception e) {
            throw new RuntimeException("Error crítico de comunicación con el servicio de Usuarios al buscar al " + rol + " ID " + id);
        }
        
        throw new IllegalArgumentException("Respuesta inválida del servidor al buscar al " + rol + " con ID " + id);
    }

    // Método de utilidad para buscar una llave en un Map considerando distintos formatos
    private String extraerTexto(Map<String, Object> mapa, String... posiblesLlaves) {
        for (String llave : posiblesLlaves) {
            if (mapa.containsKey(llave) && mapa.get(llave) != null) {
                return (String) mapa.get(llave);
            }
        }
        return null;
    }

    private void notificarApoderado(Integer idAlumno, Integer idDocente, String descripcionFalta) {
        try {
            // Buscamos la hoja de vida en el microservicio de Vida Estudiantil
            Object resHv = vidaEstudiantilWeb.get().uri("/estudiante/{id}", idAlumno)
                    .retrieve().bodyToMono(Object.class).block();
            
            if (resHv instanceof Map) {
                @SuppressWarnings("unchecked") 
                Map<String, Object> hv = (Map<String, Object>) resHv;
                @SuppressWarnings("unchecked") 
                List<Map<String, Object>> apoderados = (List<Map<String, Object>>) hv.get("antecedentesApoderado");
                
                if (apoderados != null && !apoderados.isEmpty()) {
                    Integer idApoderado = (Integer) apoderados.get(0).get("apoderadoId");
                    
                    // Preparamos los datos del payload para enviar el mensaje individual
                    Map<String, Object> mensajePayload = Map.of(
                        "msjIdEmisor", idDocente,
                        "msjIdReceptor", idApoderado,
                        "msjContenido", "Notificación automática: Se ha registrado una anotación NEGATIVA al alumno. Detalle: " + descripcionFalta
                    );

                    // Petición POST al servicio de mensajería
                    mensajeriaWeb.post()
                            .uri("/individual")
                            .bodyValue(mensajePayload)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .block();
                }
            }
        } catch (Exception e) {
            System.err.println("La anotación se guardó, pero falló el envío del mensaje automático: " + e.getMessage());
        }
    }

    private AnotacionDTO convertToDTO(Anotacion anotacion) {
        return new AnotacionDTO(
                anotacion.getId(), anotacion.getAnotDesc(), anotacion.getTipo(),
                anotacion.getFecha(), anotacion.getHora(),
                anotacion.getIdEstudiante(), anotacion.getIdDocente(),
                "N/A", "N/A"
        );
    }

    private AnotacionDTO convertEnrichedToDTO(Anotacion anotacion, String nomAlu, String nomDoc) {
        return new AnotacionDTO(
                anotacion.getId(), anotacion.getAnotDesc(), anotacion.getTipo(),
                anotacion.getFecha(), anotacion.getHora(),
                anotacion.getIdEstudiante(), anotacion.getIdDocente(),
                nomAlu, nomDoc
        );
    }
}