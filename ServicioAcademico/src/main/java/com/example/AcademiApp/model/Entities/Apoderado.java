package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "apoderado")
public class Apoderado {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int apode_id;
   @Column(nullable = false)
   private int usu_id;
   @Column(nullable = false)
   private String apode_parentesco;

}
