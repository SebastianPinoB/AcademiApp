package com.example.AcademiApp.models.dto;

import java.time.LocalDate;

public record InscripcionDTO(
    int id,
    LocalDate fecha,
    boolean estVig,
    int usuId
) {

}
