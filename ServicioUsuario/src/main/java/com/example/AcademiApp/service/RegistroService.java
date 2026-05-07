package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.request.RegistroEstudianteRequest;
import com.example.AcademiApp.model.request.RegistroRequest;
import com.example.AcademiApp.repository.ApoderadoRespository;
import com.example.AcademiApp.repository.EstudianteRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroService {

   @Autowired
   private EstudianteRepository estudianteRepository;

   @Autowired
   private ApoderadoRespository apoderadoRespository;

   // Crea usuarios y apoderados
   @Transactional
   public void registrarAlumno(RegistroRequest nuevoRegistro) {

      // No se puede duplicar un estudiante
      if (estudianteRepository.existsByNumrun(nuevoRegistro.alumno().numrun())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este alumno ya está registrado.");
      }

      // Transformar el request a entidad

      // 1. Extraemos los datos del apoderado del Request
      RegistroEstudianteRequest datosApo = nuevoRegistro.apoderado();

      // BUSCAMOS si el apoderado ya existe por su numrun
      Apoderado apoderado = apoderadoRespository.findByNumrun(datosApo.numrun()).orElseGet(() -> {
         // Si NO existe, creamos uno nuevo
         Apoderado nuevoApo = new Apoderado();
         nuevoApo.setUsu_email(datosApo.email());
         nuevoApo.setUsu_pass(datosApo.password());
         nuevoApo.setNumrun(datosApo.numrun());
         nuevoApo.setUsu_dvrun(datosApo.dvRun());
         nuevoApo.setUsu_dir(datosApo.direccion());
         nuevoApo.setUsu_nombre(datosApo.nombre());
         nuevoApo.setUsu_snombre(datosApo.segundoNombre());
         nuevoApo.setUsu_appaterno(datosApo.apellidoPaterno());
         nuevoApo.setUsu_apmaterno(datosApo.apellidoMaterno());

         return apoderadoRespository.save(nuevoApo);
      });

      // Creacion alumno
      RegistroEstudianteRequest alu = nuevoRegistro.alumno();
      Estudiante estudiante = new Estudiante();
      estudiante.setUsu_email(alu.email());
      estudiante.setUsu_pass(alu.password());
      estudiante.setNumrun(alu.numrun());
      estudiante.setUsu_dvrun(alu.dvRun());
      estudiante.setUsu_dir(alu.direccion());
      estudiante.setUsu_nombre(alu.nombre());
      estudiante.setUsu_snombre(alu.segundoNombre());
      estudiante.setUsu_appaterno(alu.apellidoPaterno());
      estudiante.setUsu_apmaterno(alu.apellidoMaterno());

      // Join de tablas
      estudiante.setApoderado(apoderado);
      estudianteRepository.save(estudiante);

   }

   // Buscar todos
   public List<Estudiante> obtenerTodosEstudiantes() {
      return estudianteRepository.findAll();
   }

   public List<Apoderado> obtenerTodosApoderados() {
      return apoderadoRespository.findAll();
   }

   // Buscar * id
   public Estudiante obtenerEstudiante(int id) {
      return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
   }

   public Apoderado buscarApoderado(int id) {
      return apoderadoRespository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apoderado no encontrado"));
   }

   //Actualizar
   @Transactional
   public void actualizarEstudiante(int id, RegistroEstudianteRequest datosNuevos) {
      Estudiante est = estudianteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

      // Actualiza campos heredados de Usuario
      est.setUsu_email(datosNuevos.email());
      est.setUsu_pass(datosNuevos.password());
      est.setNumrun(datosNuevos.numrun());
      est.setUsu_dvrun(datosNuevos.dvRun());
      est.setUsu_dir(datosNuevos.direccion());
      est.setUsu_nombre(datosNuevos.nombre());
      est.setUsu_snombre(datosNuevos.segundoNombre());
      est.setUsu_appaterno(datosNuevos.apellidoPaterno());
      est.setUsu_apmaterno(datosNuevos.apellidoMaterno());

      estudianteRepository.save(est);
   }

   // Actualizar Apoderado
   @Transactional
   public void actualizarApoderado(int id, RegistroEstudianteRequest datos) {
      Apoderado apo = apoderadoRespository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apoderado no encontrado"));

      // Actualizamos campos heredados
      apo.setUsu_nombre(datos.nombre());
      apo.setUsu_snombre(datos.segundoNombre());
      apo.setUsu_appaterno(datos.apellidoPaterno());
      apo.setUsu_apmaterno(datos.apellidoMaterno());
      apo.setUsu_email(datos.email());
      apo.setUsu_dir(datos.direccion());
      apo.setNumrun(datos.numrun());
      apo.setUsu_dvrun(datos.dvRun());

      apoderadoRespository.save(apo);
   }

   // Elimina al estudiante (El apoderado permanece)
   @Transactional
   public void eliminarEstudiante(int id) {
      if (!estudianteRepository.existsById(id)) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el estudiante");
      }
      estudianteRepository.deleteById(id);
   }

}
