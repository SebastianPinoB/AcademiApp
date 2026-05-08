package com.example.AcademiApp.model.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "apoderado")
@EqualsAndHashCode(callSuper = true)
public class Apoderado extends Usuario {

   private String apode_parentesco;
   @JsonManagedReference
   @OneToMany(mappedBy = "apoderado")
   private List<Estudiante> estudiantes = new ArrayList<>();

}
