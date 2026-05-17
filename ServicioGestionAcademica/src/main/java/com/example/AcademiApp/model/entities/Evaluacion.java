package com.example.AcademiApp.model.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "evaluacion")
public class Evaluacion {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

   private int evaId;
   private Date evaFecha;
   private String evaTipo;
   private float evaPuntaje;

   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "asig_id")
   private Asignatura asignatura;

   @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Nota> notas;

}
