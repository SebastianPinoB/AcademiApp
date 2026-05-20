package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CalendarioEstudiantilRequest {

    @NotNull(message = "El curso es obligatorio")
    @Positive
    private Integer cursoId;

    @NotNull(message = "La asignatura es obligatoria")
    @Positive
    private Integer asignaturaId;

    @NotNull(message = "El docente es obligatorio")
    @Positive
    private Integer docenteId;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser en el pasado")
    private LocalDate calEstFecha;

    @NotBlank(message = "La descripción es obligatoria")
    private String calEstDescripcion;

    @NotBlank(message = "Debe vincular al menos un OA")
    private String calEstOa;

    @NotBlank(message = "El tipo es obligatorio")
    private String calEstTipo;
}