package com.example.AcademiApp.models.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitaIndividualRequest {
    private LocalDate fecha;
    private String temas;
    private String acuerdos;
    private String observaciones; // Compromisos obligatorios
    private int usuIdEstudiante; // ID del alumno desde el Servicio Auth/Usuarios.
    private int usuIdFuncionario; // Responsable del registro.
    private String pinFirma; // PIN de firma electrónica para bloqueo.
}
