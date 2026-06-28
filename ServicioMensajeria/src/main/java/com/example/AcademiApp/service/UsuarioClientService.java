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

    private boolean existeEnListaApoderados(int usuarioId) {
        try {
            UsuarioExternoDTO[] apoderados = restTemplate.getForObject(
                USUARIO_BASE_URL + "/registro/apoderado", UsuarioExternoDTO[].class
            );
            if (apoderados == null) return false;

            for (UsuarioExternoDTO a : apoderados) {
                if (a.getUsuId() == usuarioId) return true;
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Verifica que esté corriendo en el puerto 5000."
            );
        }
    }
}