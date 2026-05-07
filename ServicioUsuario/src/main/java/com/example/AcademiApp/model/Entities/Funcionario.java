package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "funcionario")
@Inheritance(strategy = InheritanceType.JOINED) //Permite que Docente, Inspector, etc hereden de aquí
@EqualsAndHashCode(callSuper = true)
public class Funcionario extends Usuario{
   
   private String funci_titulo;

}
