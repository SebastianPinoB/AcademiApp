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
}
