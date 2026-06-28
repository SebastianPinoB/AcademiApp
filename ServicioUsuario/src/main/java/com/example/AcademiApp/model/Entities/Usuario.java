package com.example.AcademiApp.model.Entities;

import java.util.ArrayList;
import java.util.List;

import com.example.AcademiApp.model.Entities.direccion.Direccion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED) // <--- Estrategia de herencia
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuId;

    private String usuEmail;
    private String usu_pass;
    @Column(name = "numrun", unique = true, nullable = false)
    private int numrun;
    private char usu_dvrun;
    private String usu_nombre;
    private String usu_snombre;
    private String usu_appaterno;
    private String usu_apmaterno;

    // Poner roles en este microservicio
    // RELACIÓN 1 a N
    // mappedBy: indica que la relación se controla en el campo "usuario" de la
    // clase Direccion
    // cascade: si guardas/borras el usuario, se guardan/borran sus direcciones
    // orphanRemoval: si sacas una dirección de la lista, se borra de la BD
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();

    // MÉTODO DE AYUDA: Muy importante para que JPA guarde la relación correctamente
    public void agregarDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
        direccion.setUsuario(this); // Conecta la dirección con este usuario
    }

}
