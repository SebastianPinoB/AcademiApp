package com.example.AcademiApp.models.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnotacionCreateRequest {

    @NotBlank(message = "La descripción de la anotación es obligatoria")
    private String anotDesc;

    @NotBlank(message = "El tipo de anotación es obligatorio")
    private String tipo;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Integer idEstudiante;

    @NotNull(message = "El ID del docente es obligatorio")
    private Integer idDocente;
}