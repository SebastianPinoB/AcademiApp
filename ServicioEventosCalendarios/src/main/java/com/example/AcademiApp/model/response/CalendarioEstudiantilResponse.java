package com.example.AcademiApp.model.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CalendarioEstudiantilResponse {
    private int calEstId;
    private int cursoId;
    private int asignaturaId;
    private int docenteId;
    private LocalDate calEstFecha;
    private String calEstDescripcion;
    private String calEstOa;
    private String calEstTipo;
}