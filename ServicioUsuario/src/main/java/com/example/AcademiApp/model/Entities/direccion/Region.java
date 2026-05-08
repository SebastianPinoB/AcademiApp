package com.example.AcademiApp.model.Entities.direccion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Region {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int regi_id;
   private String regi_nombre;

   @ManyToOne
   @JoinColumn(name = "pais_id")
   private Pais pais;
}
