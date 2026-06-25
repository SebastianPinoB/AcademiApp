package com.example.AcademiApp.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record ActaDTO(
    Integer idActa,
    LocalDate fecha,
    LocalTime hora,
    String descripcion,
    String temasTratados,
    String acuerdos,
    String observaciones,
    
    // Aquí está la magia: en vez de IDs, devolvemos los datos reales
    String infoCurso,       
    String nombreFuncionario 
) {}