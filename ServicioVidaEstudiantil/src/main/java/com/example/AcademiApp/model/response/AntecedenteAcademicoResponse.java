package com.example.AcademiApp.model.response;

import lombok.Data;

@Data
public class AntecedenteAcademicoResponse {
    private int antAcaId;
    private int antAcaAnio;
    private double antAcaPromGen;
    private String antAcaObs;
    private String antAcaCompor;
}