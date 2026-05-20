package com.example.AcademiApp.model.response;

import lombok.Data;

@Data
public class AntecedenteMedicoResponse {
    private int antMedId;
    private int antMedEdad;
    private double antMedPeso;
    private double antMedAltura;
    private String antMedGrupoSang;
    private String antMedPats;
    private String antMedFarmaco;
    private String antMedObs;
}