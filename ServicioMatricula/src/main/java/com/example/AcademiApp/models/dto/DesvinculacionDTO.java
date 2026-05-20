package com.example.AcademiApp.models.dto;

import java.time.LocalDate;

public record DesvinculacionDTO(
    int usuId,
    String motivo,
    LocalDate fechaDesvinculacion
) {

}
