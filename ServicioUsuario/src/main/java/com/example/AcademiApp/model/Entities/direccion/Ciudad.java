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
public class Ciudad {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int ciudad_id;
   private String ciudad_nombre;

   @ManyToOne
   @JoinColumn(name = "regi_id")
   private Region region;

}
