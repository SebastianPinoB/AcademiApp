package com.example.AcademiApp.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarUsuario {
    @NotBlank
    private String usu_email;
    @NotBlank
    private String usu_pass;
    @NotBlank
    private String usu_dir;

    private char usu_snombre;
    private char usu_nombre;
}
