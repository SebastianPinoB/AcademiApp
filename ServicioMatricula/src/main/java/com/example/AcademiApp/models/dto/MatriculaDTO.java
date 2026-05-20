package com.example.AcademiApp.models.dto;

import java.time.LocalDate;

public record MatriculaDTO(
    int id,
    LocalDate fecha,
    boolean estVig,
    int usuId
) {

}
