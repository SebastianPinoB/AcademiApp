package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CrearApoderado {
   @NotNull
   @Positive
   private Integer usu_id;

   @NotNull
   private String apode_parentesco;

}
