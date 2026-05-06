package com.example.AcademiApp.model.dto;

import com.example.AcademiApp.model.request.CrearApoderado;
import com.example.AcademiApp.model.request.CrearEstudiante;

import lombok.Data;

@Data
public class RegistroAlumnoWrapper {
   private CrearEstudiante nuevoEst;
    private CrearApoderado nuevoApo;
}
