package com.example.AcademiApp.model.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "estudiante")
@EqualsAndHashCode(callSuper = true)
public class Estudiante extends Usuario {
   private String estu_parentesco;
   
   @ManyToOne
   @JoinColumn(name = "apode_id")
   @JsonBackReference
   private Apoderado apoderado;

}
