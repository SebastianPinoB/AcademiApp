package com.example.AcademiApp.model.dto;

import lombok.Data;

@Data
public class UsuarioExternoDTO {
    private int usuId;
    private String usuEmail;
    private int numrun;
    private String usuNombre;
    private String usuSnombre;
    private String usuAppaterno;
    private String usuApmaterno;
}