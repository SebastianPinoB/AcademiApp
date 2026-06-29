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

    public void validarUsuarioExiste(int usuarioId) {
        if (intentarObtenerPorId("/registro/alumno/" + usuarioId)) return;
        if (intentarObtenerPorId("/registro/funcionario/" + usuarioId)) return;
        if (existeEnListaApoderados(usuarioId)) return;

        throw new IllegalArgumentException(
            "No existe un usuario con ID: " + usuarioId + " en el Servicio de Usuarios."
        );
    }

    private boolean intentarObtenerPorId(String path) {
        try {
            restTemplate.getForObject(USUARIO_BASE_URL + path, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Error: " + e.getMessage()
            );
        }
    }

    private boolean existeEnListaApoderados(int usuarioId) {
        try {
            java.util.List apoderados = restTemplate.getForObject(
                USUARIO_BASE_URL + "/registro/apoderado", java.util.List.class
            );
            if (apoderados == null) return false;

            for (Object a : apoderados) {
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) a;
                Object usuId = map.get("usuId");
                if (usuId != null && Integer.parseInt(usuId.toString()) == usuarioId) return true;
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Error: " + e.getMessage()
            );
        }
    }
}