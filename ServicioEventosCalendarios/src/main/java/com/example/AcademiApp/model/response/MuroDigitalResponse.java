package com.example.AcademiApp.model.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MuroDigitalResponse {
    private int muroDigId;
    private int docenteId;
    private int asignaturaId;
    private String muroConte;
    private LocalDateTime muroFecPubli;
    private String muroTipoConte;
}