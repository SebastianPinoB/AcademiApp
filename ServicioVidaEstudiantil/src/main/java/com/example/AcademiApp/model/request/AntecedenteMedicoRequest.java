package com.example.AcademiApp.model.request;

import lombok.Data;

@Data
public class AntecedenteMedicoRequest {
    
    private Integer antMedEdad;
    private Double antMedPeso;
    private Double antMedAltura;
    private String antMedGrupoSang;
    private String antMedPats; //patologias
    private String antMedFarmaco;
    private String antMedObs; //observaciones

}
