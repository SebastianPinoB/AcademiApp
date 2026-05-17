package com.example.AcademiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.model.entities.Curso;
import com.example.AcademiApp.model.entities.Nivel;
import com.example.AcademiApp.model.entities.Sala;
import com.example.AcademiApp.model.request.CursoRequest;
import com.example.AcademiApp.repository.CursoRepository;
import com.example.AcademiApp.repository.NivelRepository;
import com.example.AcademiApp.repository.SalaRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService {

   @Autowired
   private CursoRepository cursoRepository;
   @Autowired
   private SalaRepository salaRepository;
   @Autowired
   private NivelRepository nivelRepository;

   @Transactional
   public Curso registrarCurso(CursoRequest request) {

      //Busca por nombre. Si no existe, crea uno nuevo
      Nivel nivel = nivelRepository.findByNivelNombre(request.getNivel().getNivelNombre())
            .orElseGet(() -> {
               Nivel nuevoNivel = new Nivel();
               nuevoNivel.setNivelNombre(request.getNivel().getNivelNombre());
               // No hace falta hacer nivelRepository.save() gracias al CascadeType.PERSIST en
               // Curso
               return nuevoNivel;
            });

      //Busca por nombre. Si no existe, crea uno nuevo
      Sala sala = salaRepository.findBySalaNombre(request.getSala().getSalaNombre())
      .orElseGet(() ->{
         Sala nuevaSala = new Sala();
         nuevaSala.setSalaNombre(request.getSala().getSalaNombre());
         nuevaSala.setSalaCapacidad(request.getSala().getSalaCapacidad());
         return nuevaSala;
      });

      // Crea y arma la entidad curso
      Curso curso = new Curso();
      curso.setCursoLetra(request.getCursoLetra());
      curso.setNivel(nivel);
      curso.setSala(sala);

      return cursoRepository.save(curso);

   }

}
