package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroInspectorRequest (
   // Datos de Usuario
   @NotBlank
   String email,
   @NotBlank
   String password,
   @NotNull
   Integer numrun,
   @NotNull
   Character dvRun,
   @NotBlank
   String nombre,
   String segundoNombre,
   @NotBlank
   String apellidoPaterno,
   @NotBlank
   String apellidoMaterno,
   String direccion,

   // Datos de Funcionario
   @NotBlank
   String titulo,

   // Datos de Inspector
   @NotBlank
   String nivel 

){}
