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
@Table(name = "nivel")
public class Nivel {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   
   private int nivelId;
   private String nivelNombre;

   // Relacion opcional para acceder a los cursos desde el nivel
   @OneToMany(mappedBy = "nivel")
   @JsonIgnore
   private List<Curso> cursos;

}
