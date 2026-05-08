package com.example.AcademiApp.model.Entities.direccion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Pais {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int pais_id;
   private String pais_nombre;
}
