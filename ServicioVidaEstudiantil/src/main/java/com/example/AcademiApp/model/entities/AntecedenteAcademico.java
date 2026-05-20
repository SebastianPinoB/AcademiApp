package com.example.AcademiApp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "antecedente_academico")
public class AntecedenteAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int antAcaId;

    @Column(nullable = false)
    private int antAcaAnio;

    private double antAcaPromGen;

    @Column(columnDefinition = "TEXT")
    private String antAcaObs; //observaciones

    @Column(columnDefinition = "TEXT")
    private String antAcaCompor; //comportamiento academico

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hoja_id", nullable = false)
    private HojaVidaEstudiante hojaVida;
}