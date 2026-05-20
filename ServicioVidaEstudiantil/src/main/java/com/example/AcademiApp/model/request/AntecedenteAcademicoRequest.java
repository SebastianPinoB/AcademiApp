package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AntecedenteAcademicoRequest {
    
    @NotNull(message = "El año académico es obligatorio")
    private int antAcaAnio;

    private double antAcaPromGen;
    private String antAcaObs; //observaciones
    private String antAcaCompor; //comportamiento academico
}
