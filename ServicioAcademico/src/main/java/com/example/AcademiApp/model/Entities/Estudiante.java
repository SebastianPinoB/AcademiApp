package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Column;
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
@Table(name = "estudiante")
public class Estudiante {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int estu_id;
   @Column(nullable = false)
   private int usu_id;
   @Column(nullable = false)
   private String estu_parentesco;

   @ManyToOne
   @JoinColumn(name = "apode_id", nullable = false)
   private Apoderado apoderado;

}
