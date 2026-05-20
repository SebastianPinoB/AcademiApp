package com.example.AcademiApp.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "mensajeria")
public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msjId;

    @Column(nullable = false)
    private int msjIdEmisor; 

    private Integer msjIdReceptor;

    private Integer cursoId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String msjContenido;

    @Column(nullable = false)
    private LocalDateTime msjFechaEnvio;

    @Column(nullable = false)
    private String msjTipo;

    @Column(nullable = false)
    private String msjEstado;
}