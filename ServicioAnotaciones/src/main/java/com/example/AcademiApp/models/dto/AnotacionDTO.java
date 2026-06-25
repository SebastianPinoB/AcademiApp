package com.example.AcademiApp.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AnotacionDTO(
    Long id,
    String anotDesc,
    String tipo,
    LocalDate fecha,
    LocalTime hora,
    Integer idEstudiante,
    Integer idDocente
) {}