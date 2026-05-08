package com.example.AcademiApp.model.request;

import java.util.List;

import com.example.AcademiApp.model.request.direccion.DireccionRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

   @NotEmpty(message = "Debe ingresar al menos una dirección")
   List<DireccionRequest> direcciones, // <--- CAMBIO: Ahora es una lista
   
   // Datos de Funcionario
   @NotBlank
   String titulo,

   // Datos de Inspector
   @NotBlank
   String nivel 
   

){
}
