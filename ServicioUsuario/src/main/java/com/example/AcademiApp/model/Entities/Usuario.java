package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED) // <--- Estrategia de herencia
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usu_id;

    
    private String usu_email;
    private String usu_pass;
    private int numrun;
    private char usu_dvrun;
    private String usu_dir;
    private String usu_nombre;
    private String usu_snombre;
    private String usu_appaterno;
    private String usu_apmaterno;
    
    //Poner roles en este microservicio

}
