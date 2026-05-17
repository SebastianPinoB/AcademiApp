package com.example.AcademiApp.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "sala")
public class Sala {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int salaId;
   private String salaNombre;
   private int salaCapacidad; 

   // Relacion opcional para acceder a los cursos desde la sala
   @OneToMany(mappedBy = "sala")
   @JsonIgnore
   private List<Curso> cursos;
}
