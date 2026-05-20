package com.example.AcademiApp.models.request;

import java.time.LocalDate;

import lombok.Data;

@Data   
public class MatriculaRequest {
    private int usuId;
    private LocalDate fecha;
    private boolean estVig;
}
