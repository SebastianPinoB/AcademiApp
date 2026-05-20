package com.example.AcademiApp.models.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DesvinculacionRequest {
    private int usuId;
    private String motivo;
    private LocalDate fechaDesvinculacion;
}
