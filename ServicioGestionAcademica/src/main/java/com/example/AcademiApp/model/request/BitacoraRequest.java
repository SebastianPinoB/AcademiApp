package com.example.AcademiApp.model.request;

import java.util.Date;

import lombok.Data;

@Data
public class BitacoraRequest {
   private String bitNombre;
   private Date bitFechaRegistro;
   private Date bitFechaRealClase;
   private String bitObjAprend;
   private String bitTemasTratadoClase;
   private java.time.LocalTime bitHoraIni;
   private java.time.LocalTime bitHoraFin;
   private int asignaturaId; // ID de la asignatura a la que pertenece la bitácora
}
