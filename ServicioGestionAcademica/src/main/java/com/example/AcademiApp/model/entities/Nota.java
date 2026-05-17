package com.example.AcademiApp.model.entities;

import java.util.Date;

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
@Table(name = "notas")
public class Nota {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int notaId;
   private float notaValor;
   private Date notaFecha;

   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "eva_id")
   private Evaluacion evaluacion;
}