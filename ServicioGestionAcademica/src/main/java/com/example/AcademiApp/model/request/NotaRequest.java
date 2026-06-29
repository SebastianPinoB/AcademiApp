package com.example.AcademiApp.model.request;

import java.util.Date;

import lombok.Data;

@Data
public class NotaRequest {
   private float notaValor;
   private Date notaFecha;
   private int evaluacionId;
   private int estudianteId;
}
