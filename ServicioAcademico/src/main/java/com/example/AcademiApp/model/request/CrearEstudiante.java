package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CrearEstudiante {
   @NotNull
   @Positive
   private Integer usu_id;

   @NotNull
   private String estu_parentesco;

   @NotNull
   @Positive
   private Integer apode_id;
}
