package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "directivo")
@EqualsAndHashCode(callSuper = true)
public class Directivo extends Funcionario{
   private String direct_cargo;
}
