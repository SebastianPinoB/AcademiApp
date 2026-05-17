package com.example.AcademiApp.model.request;

import lombok.Data;

@Data
public class CursoRequest {
   private String cursoLetra;
   private SalaRequest sala; // Datos de la sala
   private NivelRequest nivel; // Datos del nivel

   @Data
   public static class SalaRequest {
      private String salaNombre;
      private int salaCapacidad;
   }

   @Data
    public static class NivelRequest {
        private String nivelNombre;
    }
}
