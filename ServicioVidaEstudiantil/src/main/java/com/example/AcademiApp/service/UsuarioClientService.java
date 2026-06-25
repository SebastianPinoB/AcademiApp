package com.example.AcademiApp.service;

import com.example.AcademiApp.model.dto.UsuarioExternoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioClientService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USUARIO_BASE_URL = "http://localhost:5000";

    /**
     * Consulta al ServicioUsuario si existe un alumno con ese ID.
     * Lanza excepción si no existe o el servicio no responde.
     */
    public UsuarioExternoDTO obtenerEstudiante(int estudianteId) {
        try {
            String url = USUARIO_BASE_URL + "/registro/alumno/" + estudianteId;
            return restTemplate.getForObject(url, UsuarioExternoDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException(
                "No existe un estudiante con ID: " + estudianteId + " en el Servicio de Usuarios."
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Verifica que esté corriendo en el puerto 5000."
            );
        }
    }

    public UsuarioExternoDTO obtenerApoderado(int apoderadoId) {
        try {
            String url = USUARIO_BASE_URL + "/registro/apoderado";
            UsuarioExternoDTO[] apoderados = restTemplate.getForObject(url, UsuarioExternoDTO[].class);

            if (apoderados == null) {
                throw new IllegalArgumentException("No se pudo obtener la lista de apoderados.");
            }

            for (UsuarioExternoDTO apoderado : apoderados) {
                if (apoderado.getUsuId() == apoderadoId) {
                    return apoderado;
                }
            }

            throw new IllegalArgumentException(
                "No existe un apoderado con ID: " + apoderadoId + " en el Servicio de Usuarios."
            );

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Verifica que esté corriendo en el puerto 5000."
            );
        }
    }

}