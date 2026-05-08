package com.example.AcademiApp.model.response;

import java.util.List;

public record ApoderadoResponse(
      int id,
      String nombreCompleto,
      String email,
      String parentesco,
      List<String> nombresEstudiantes // Para listar a sus hijos/pupilos
) {

}
