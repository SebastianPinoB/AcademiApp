package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MuroDigitalRequest {

    @NotNull(message = "El docente es obligatorio")
    @Positive
    private Integer docenteId;

    @NotNull(message = "La asignatura es obligatoria")
    @Positive
    private Integer asignaturaId;

    @NotBlank(message = "El contenido es obligatorio")
    private String muroConte;

    @NotBlank(message = "El tipo de contenido es obligatorio")
    private String muroTipoConte;
}