package com.example.AcademiApp.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bitacora_reu_general")
public class BitacoraReuGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReunGeneral;

    private LocalDate fecha;
    private LocalTime hora;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(columnDefinition = "TEXT")
    private String temasTratados;

    @Column(columnDefinition = "TEXT")
    private String acuerdos;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private int idFuncionario; // ID del funcionario que registró la reunión

}
