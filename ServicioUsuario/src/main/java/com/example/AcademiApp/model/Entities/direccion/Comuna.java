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
public class Comuna {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int comu_id;
   private String comu_nombre;

   @ManyToOne
   @JoinColumn(name = "ciudad_id")
   private Ciudad ciudad;

}
