package com.example.AcademiApp.models.requests;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ActaRequest {
    private LocalDate fecha;
    private LocalTime hora;
    private String temas;
    private String acuerdos;
    private String observaciones;
    private Integer cursoId; 
    private int usuIdResponsable; // ID validado en el Servicio Auth.
}
