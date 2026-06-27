package com.example.AcademiApp.model.dto;

import lombok.Data;

@Data
public class CursoExternoDTO {
    private int cursoId;
    private String cursoLetra;
    private NivelDTO nivel;

    @Data
    public static class NivelDTO {
        private int nivelId;
        private String nivelNombre;
    }
}