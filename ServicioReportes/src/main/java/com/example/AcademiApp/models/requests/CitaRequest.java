package com.example.AcademiApp.models.requests;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CitaRequest {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "La descripción de la cita es obligatoria")
    private String descripcion;

    private String temasTratados;
    private String acuerdos;
    private String observaciones;

    @NotNull(message = "El ID del estudiante (usuId) es obligatorio")
    private Integer usuId;

    // Estos pueden venir nulos al crear, asumiendo que se firman después
    private Boolean bitFirmaApo;
    private String firmaDocente;
}