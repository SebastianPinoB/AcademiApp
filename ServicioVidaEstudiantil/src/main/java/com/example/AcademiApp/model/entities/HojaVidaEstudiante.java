package com.example.AcademiApp.model.entities;

import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "hoja_vida_estudiante")
public class HojaVidaEstudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hojaId;

    @Column(nullable = false, unique = true)
    private int estudianteId;

    @OneToOne(mappedBy = "hojaVida", cascade = CascadeType.ALL, orphanRemoval = true)
    private AntecedenteMedico antecedenteMedico;

    @OneToMany(mappedBy = "hojaVida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AntecedenteAcademico> antecedentesAcademicos = new ArrayList<>();

    @OneToMany(mappedBy = "hojaVida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AntecedenteApoderado> antecedentesApoderado = new ArrayList<>();


}