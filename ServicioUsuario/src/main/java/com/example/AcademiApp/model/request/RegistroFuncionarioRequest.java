package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroFuncionarioRequest(
    @NotBlank
    String email,
    @NotBlank
    String password,
    @NotNull
    Integer numrun,
    @NotNull
    Character dvRun,
    @NotBlank
    String nombre,
    String segundoNombre,
    @NotBlank
    String apellidoPaterno,
    @NotBlank
    String apellidoMaterno,
    String direccion,
    
    // Campos específicos de funcionario
    @NotBlank 
    String titulo
) {}