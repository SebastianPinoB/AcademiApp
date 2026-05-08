package com.example.AcademiApp.model.response;

public record FuncionarioResponse(
      int id,
      String nombreCompleto,
      String email,
      String cargo,
      String especialidad) {

}
