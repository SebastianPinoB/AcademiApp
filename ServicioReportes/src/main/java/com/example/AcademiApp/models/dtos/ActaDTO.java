package com.example.AcademiApp.models.dtos;

import java.time.LocalDate;

public record ActaDTO (
    int id,
    String tipoActa,
    LocalDate fecha,
    String resumenActa,
    boolean estaFirmada
){

}
