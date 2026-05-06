package com.example.AcademiApp.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroEstudianteRequest(

    @Email
    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotNull
    Integer numRun,

    @NotNull
    Character dvRun,

    @NotBlank
    String nombre,

    String segundoNombre,

    @NotBlank
    String apellidoPaterno,

    @NotBlank
    String apellidoMaterno,

    String direccion

) {}
