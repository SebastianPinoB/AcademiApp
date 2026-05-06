package com.example.AcademiApp.model.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usu_id;
    @Column(nullable = false)
    private String usu_email;
    @Column(nullable = false)
    private String usu_pass;
    @Column(nullable = false)
    private int usu_numrun;
    @Column(nullable = false)
    private char usu_dvrun;
    @Column(nullable = false)
    private String usu_dir;
    @Column(nullable = false)
    private String usu_nombre;

    private String usu_snombre;

    @Column(nullable = false)
    private String usu_appaterno;
    @Column(nullable = false)
    private String usu_apmaterno;
    
    //Poner roles en este microservicio

}
