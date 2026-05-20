package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AntecedenteApoderadoRequest {

    @NotNull(message = "El ID del apoderado es obligatorio")
    private int apoderadoId;
    @NotBlank(message = "El número de teléfono es obligatorio")
    private String antApoNumTelf;
    private String antApoMail;
    private String antApoProfesion;
    private String antApoLugarTrab; //lugar trabajo

}
