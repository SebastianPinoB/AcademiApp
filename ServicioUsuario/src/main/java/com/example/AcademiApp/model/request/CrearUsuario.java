package com.example.AcademiApp.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CrearUsuario {
    @NotBlank
    private String usu_email;
    @NotBlank
    private String usu_pass;
    @NotBlank
    private int usu_numrun;
    @NotBlank
    private char usu_dvrun;
    @NotBlank
    private String usu_nombre;
    @NotBlank
    private String usu_dir;

    private String usu_snombre;
    @NotBlank
    private String usu_appaterno;
    @NotBlank
    private String usu_apmaterno;
}
