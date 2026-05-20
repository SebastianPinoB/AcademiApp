package com.example.AcademiApp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "antecedente_medico")
public class AntecedenteMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int antMedId;

    @Column(nullable = false)
    private int antMedEdad;

    private double antMedPeso;
    private double antMedAltura;
    private String antMedGrupoSang;
    private String antMedPats; //patologias
    private String antMedFarmaco;

    @Column(columnDefinition = "TEXT")
    private String antMedObs; //observaciones

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "hoja_id", nullable = false)
    private HojaVidaEstudiante hojaVida;
}
