package com.example.AcademiApp.models.dtos;

import java.time.LocalDate;

public record CitaDTO (
    int id,
    LocalDate fecha,
    int idEstudiante,
    String acuerdosPrincipales,
    boolean bloqueado
){

}
