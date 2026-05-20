package com.example.AcademiApp.model.response;

import lombok.Data;
import java.util.List;

@Data
public class HojaVidaResponse {
    private int hojaId;
    private int estudianteId;
    private AntecedenteMedicoResponse antecedenteMedico;
    private List<AntecedenteAcademicoResponse> antecedentesAcademicos;
    private List<AntecedenteApoderadoResponse> antecedentesApoderado;
}