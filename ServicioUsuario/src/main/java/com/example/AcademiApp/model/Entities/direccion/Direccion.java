package com.example.AcademiApp.model.Entities.direccion;

import com.example.AcademiApp.model.Entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "direccion")
public class Direccion {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int add_id;

   private String add_calle;
   private int add_numero;
   private String add_letra;

   @ManyToOne
   @JoinColumn(name = "usu_id") // Clave foránea hacia la tabla Usuario
   @JsonIgnore
   private Usuario usuario;

   @ManyToOne
   @JoinColumn(name = "comu_id")
   private Comuna comuna;

}
