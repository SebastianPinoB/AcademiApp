package com.example.AcademiApp.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegistroRequest(
   @Valid
   @NotNull
   RegistroEstudianteRequest apoderado,

   @Valid
   @NotNull
   RegistroEstudianteRequest alumno
) {
}
