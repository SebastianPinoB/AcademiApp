package com.example.AcademiApp.model.response;

public record EstudianteResponse(

      int id,
      String nombre,
      String apellidoPaterno,
      String email,
      String parentesco,
      String nombreApoderado, // Solo el nombre para evitar el objeto circular
      Integer apoderadoId //agrego campo para devolver el id de su apoderado en hojavida
) {
}
