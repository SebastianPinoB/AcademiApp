package com.example.AcademiApp.model.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "asignatura")
public class Asignatura {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int asigId;

   @Column(nullable = false)
   private String asigNombre;
   @Column(nullable = false)
   private String asigDesc;

   //orphanRemoval para que el objeto hijo se elimina si el padre no existe
   // cualquiercambio que le hagas a una entidad "padre" se aplicara automáticamente
   // a todas sus entidades hijas 
   @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Evaluacion> evaluaciones;
   
   //cascadeType.ALL
   //Guardar Asignatura -> guarda Evaluaciones
   //Eliminar Asignatura -> elimina Evaluaciones
   @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<BitacoraAsignatura> bitacoras;

}
