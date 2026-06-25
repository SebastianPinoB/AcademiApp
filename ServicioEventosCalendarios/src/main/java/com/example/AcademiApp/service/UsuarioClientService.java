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

    public UsuarioExternoDTO obtenerDocente(int docenteId) {
        try {
            String url = USUARIO_BASE_URL + "/registro/funcionario/" + docenteId;
            return restTemplate.getForObject(url, UsuarioExternoDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException(
                "No existe un docente con ID: " + docenteId + " en el Servicio de Usuarios."
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Usuarios. Verifica que esté corriendo en el puerto 5000."
            );
        }
    }
}