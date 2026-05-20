package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AcademiApp.model.entities.Curso;
import com.example.AcademiApp.model.entities.Nivel;
import com.example.AcademiApp.model.entities.Sala;
import com.example.AcademiApp.model.request.CursoRequest;
import com.example.AcademiApp.repository.CursoRepository;
import com.example.AcademiApp.repository.NivelRepository;
import com.example.AcademiApp.repository.SalaRepository;

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

      // Busca por nombre. Si no existe, crea uno nuevo
      Nivel nivel = nivelRepository.findByNivelNombre(request.getNivel().getNivelNombre())
            .orElseGet(() -> {
               Nivel nuevoNivel = new Nivel();
               nuevoNivel.setNivelNombre(request.getNivel().getNivelNombre());
               // No hace falta hacer nivelRepository.save() gracias al CascadeType.PERSIST en
               // Curso
               return nuevoNivel;
            });

      // Busca por nombre. Si no existe, crea uno nuevo
      Sala sala = salaRepository.findBySalaNombre(request.getSala().getSalaNombre())
            .orElseGet(() -> {
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

   @Transactional(readOnly = true)
   public List<Curso> obtenerTodos() {
      return cursoRepository.findAll();
   }

   @Transactional(readOnly = true)
   public Curso obtenerPorId(int id) {
      return cursoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + id));
   }

   @Transactional
   public Curso actualizarCurso(int id, CursoRequest request) {
      Curso cursoExistente = cursoRepository.findById(id)
            .orElseThrow(
                  () -> new IllegalArgumentException("No se puede actualizar: Curso no encontrado con ID: " + id));

      // Reutiliza o crea el nivel si viene uno nuevo en la edición
      Nivel nivel = nivelRepository.findByNivelNombre(request.getNivel().getNivelNombre())
            .orElseGet(() -> {
               Nivel nuevoNivel = new Nivel();
               nuevoNivel.setNivelNombre(request.getNivel().getNivelNombre());
               return nuevoNivel;
            });

      // Reutiliza o crea la sala si viene una nueva en la edición
      Sala sala = salaRepository.findBySalaNombre(request.getSala().getSalaNombre())
            .orElseGet(() -> {
               Sala nuevaSala = new Sala();
               nuevaSala.setSalaNombre(request.getSala().getSalaNombre());
               nuevaSala.setSalaCapacidad(request.getSala().getSalaCapacidad());
               return nuevaSala;
            });

      // Actualizamos los datos del curso
      cursoExistente.setCursoLetra(request.getCursoLetra());
      cursoExistente.setNivel(nivel);
      cursoExistente.setSala(sala);

      return cursoRepository.save(cursoExistente);
   }

   @Transactional
   public void eliminarCurso(int id) {
      if (!cursoRepository.existsById(id)) {
         throw new IllegalArgumentException("No se puede eliminar: Curso no encontrado con ID: " + id);
      }
      cursoRepository.deleteById(id);
   }

}
