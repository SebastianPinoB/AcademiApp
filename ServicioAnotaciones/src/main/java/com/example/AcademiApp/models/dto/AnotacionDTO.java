package com.example.AcademiApp.models.dto;

import java.time.LocalDate;

public record AnotacionDTO(
    Long id,
    String tipo,
    String descripcion,
    LocalDate fecha,
    String nombreDocente // Se obtendría consultando al Auth
) {

}
