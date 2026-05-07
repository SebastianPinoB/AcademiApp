package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "inspector")
@EqualsAndHashCode(callSuper = true)
public class Inspector extends Funcionario{
   private String inspec_nivel;
}
