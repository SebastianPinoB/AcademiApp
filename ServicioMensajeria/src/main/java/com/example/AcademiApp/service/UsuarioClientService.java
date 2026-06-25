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
     * Verifica que un usuario exista en CUALQUIER rol (alumno, apoderado o funcionario).
     * Prueba cada endpoint hasta encontrar coincidencia.
     */
    public void validarUsuarioExiste(int usuarioId) {
        if (intentarObtener("/registro/alumno/" + usuarioId)) return;
        if (intentarObtener("/registro/apoderado/" + usuarioId)) return;
        if (intentarObtener("/registro/funcionario/" + usuarioId)) return;

        throw new IllegalArgumentException(
            "No existe un usuario con ID: " + usuarioId + " en el Servicio de Usuarios."
        );
    }

    private boolean intentarObtener(String path) {
        try {
            restTemplate.getForObject(USUARIO_BASE_URL + path, UsuarioExternoDTO.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Verifica que esté corriendo en el puerto 5000."
            );
        }
    }
}