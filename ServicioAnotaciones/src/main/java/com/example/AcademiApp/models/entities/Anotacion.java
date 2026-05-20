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
@Table(name = "anotaciones")
public class Anotacion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String anotDesc; 

    private String tipo;
    private LocalDate fecha;
    private LocalTime hora;

    // Referencias lógicas a otros microservicios
    private int idEstudiante; // Proviene de Servicio Auth / Gestión Estudiantes
    private int idDocente;    // Proviene de Servicio Auth

}
