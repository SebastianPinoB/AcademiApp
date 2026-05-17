package com.example.AcademiApp.model.entities;

import jakarta.persistence.CascadeType;
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
@Table(name = "curso")
public class Curso {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   
   private int cursoId;
   private String cursoLetra;

   // cascade para a la hora de buscar aplique la logica "Buscar o crear"
   //"Si te paso una Sala o Nivel nuevos que aún no están en la base de datos, 
   // guárdalos primero automáticamente antes de guardar el curso".
   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "nivel_id")
   private Nivel nivel;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "sala_id")
   private Sala sala;
   
}
