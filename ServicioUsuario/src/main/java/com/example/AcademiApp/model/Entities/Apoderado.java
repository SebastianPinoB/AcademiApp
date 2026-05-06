package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "apoderado")
@EqualsAndHashCode(callSuper = true)
public class Apoderado extends Usuario{

   private String apode_parentesco;

}
