package com.example.AcademiApp.models.request;

import lombok.Data;

@Data
public class AnotacionRequest {
    private String descripcion;
    private String tipo;
    private int idEstudiante;
    private int idDocente;
}
