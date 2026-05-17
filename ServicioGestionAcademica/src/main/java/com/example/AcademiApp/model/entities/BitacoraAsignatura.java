package com.example.AcademiApp.model.entities;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bitacorta asignatura")
public class BitacoraAsignatura {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int bitAsignId;

   private String bitNombre;
   private Date bitFechaRegistro;
   private Date bitFechaRealClase;
   private String bitObjetivoAprendizaje;
   private String bitTemaTratadoClase;

   // "HH:mm:ss" o "HH:mm"
   @JsonFormat(pattern = "HH:mm")
   private LocalTime bitHoraInicio; // "bitHoraInicio": "08:30:00",
   @JsonFormat(pattern = "HH:mm")
   private LocalTime bitHoraFin; // "bitHoraFin": "17:45:00"

   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "asig_id")
   private Asignatura asignatura;

}
