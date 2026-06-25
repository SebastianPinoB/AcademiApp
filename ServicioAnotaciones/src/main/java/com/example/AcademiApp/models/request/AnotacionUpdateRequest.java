package com.example.AcademiApp.models.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnotacionUpdateRequest {

    @NotBlank(message = "La descripción no puede estar vacía si se va a actualizar")
    private String anotDesc;

    private String tipo;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer idEstudiante;
    private Integer idDocente;
}