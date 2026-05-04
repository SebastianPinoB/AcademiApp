package com.example.AcademiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AcademiApp.model.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
