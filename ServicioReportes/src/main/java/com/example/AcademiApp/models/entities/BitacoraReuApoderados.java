package com.example.AcademiApp.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bitacora_reu_apoderados")
public class BitacoraReuApoderados {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idBitacoraReuApoderados;
    private LocalDate fecha;
    private LocalTime hora;

    private String descripcion;
    private String temasTratados;
    private String acuerdos;
    private String observaciones;

    private int cursoId;

}
