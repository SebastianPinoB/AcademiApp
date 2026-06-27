package com.example.AcademiApp.service;

import com.example.AcademiApp.model.dto.CursoExternoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GestionAcademicaClientService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GESTION_ACADEMICA_URL = "http://localhost:5008";

    public CursoExternoDTO[] obtenerCursos() {
        try {
            return restTemplate.getForObject(GESTION_ACADEMICA_URL + "/cursos", CursoExternoDTO[].class);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "No se pudo conectar con el Servicio de Gestión Académica. Verifica que esté corriendo en el puerto 5008."
            );
        }
    }
}