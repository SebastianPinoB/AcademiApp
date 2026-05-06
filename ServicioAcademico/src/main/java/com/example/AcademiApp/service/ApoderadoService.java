package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.repository.ApoderadoRepository;

public class ApoderadoService {

   public ApoderadoRepository apoderadoRepository;

   public List<Apoderado> obtenerTodos() {
      return apoderadoRepository.findAll();
   }

   public String eliminarApoderado(int idUsuario) {
      if (apoderadoRepository.existsById(idUsuario)) {
         apoderadoRepository.deleteById(idUsuario);
         return "Usuario eliminado correctamente";
      } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
      }
   }

}
