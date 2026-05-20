package com.example.AcademiApp.model.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "calendario_estudiantil")
public class CalendarioEstudiantil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calEstId;

    @Column(nullable = false)
    private int cursoId; //fk gestion academica

    @Column(nullable = false)
    private int asignaturaId; //fk 

    @Column(nullable = false)
    private int docenteId; //fk servicio usuario docente

    @Column(nullable = false)
    private LocalDate calEstFecha;

    @Column(nullable = false)
    private String calEstDescripcion;

    @Column(nullable = false)
    private String calEstOa; //OA objetivos de aprendizaje 

    @Column(nullable = false)
    private String calEstTipo; //tipo evaluacion,trabajo o evento etc

}
