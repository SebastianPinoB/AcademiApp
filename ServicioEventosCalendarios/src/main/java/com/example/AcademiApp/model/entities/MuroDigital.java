package com.example.AcademiApp.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "muro_digital")
public class MuroDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int muroDigId;

    @Column(nullable = false)
    private int docenteId; //fk servicio usuario docente

    @Column(nullable = false)
    private int asignaturaId; //fk gestion academica

    @Column(nullable = false, columnDefinition = "TEXT")
    private String muroConte;

    @Column(nullable = false)
    private LocalDateTime muroFecPubli;

    @Column(nullable = false)
    private String muroTipoConte;
}