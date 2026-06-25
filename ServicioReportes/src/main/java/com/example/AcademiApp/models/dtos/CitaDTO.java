package com.example.AcademiApp.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaDTO(
    Integer idBitacoraCitaApoderado,
    LocalDate fecha,
    LocalTime hora,
    String descripcion,
    String temasTratados,
    String acuerdos,
    String observaciones,
    
    // Cambiamos el Integer usuId por el nombre real enriquecido
    String nombreEstudiante, 
    
    Boolean bitFirmaApo,
    String firmaDocente
) {}