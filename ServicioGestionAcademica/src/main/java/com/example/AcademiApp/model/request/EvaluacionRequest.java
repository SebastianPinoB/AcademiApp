package com.example.AcademiApp.model.request;

import java.util.Date;

import lombok.Data;

@Data
public class EvaluacionRequest {
   private Date evaFecha;
   private String evaTipo;
   private int evaPuntaje;
   private int asignaturaId; // ID de la asignatura a la que se le asigna
}
