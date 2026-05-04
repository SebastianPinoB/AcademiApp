package com.example.AcademiApp.model.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CrearUsuario {
    private String usu_email;
    private String usu_pass;
    private int usu_numrun;
    private char usu_dvrun;
}
