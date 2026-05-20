package com.example.AcademiApp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "antecedente_apoderado")
public class AntecedenteApoderado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int antApoId;

    @Column(nullable = false)
    private int apoderadoId;

    @Column(nullable = false)
    private String antApoNumTelf;

    private String antApoMail;

    @Column(nullable = false)
    private String antApoProfesion;

    private String antApoLugarTrab; //lugar trabajo

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hoja_id", nullable = false)
    private HojaVidaEstudiante hojaVida;
}
