package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "docente")
@EqualsAndHashCode(callSuper = true)
public class Docente extends Funcionario{
   private String docen_espec;
}
