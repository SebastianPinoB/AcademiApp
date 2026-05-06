package com.example.AcademiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

   @Transactional
   public void registrarAlumno(RegistroRequest nuevoRegistro) {

      // Transformar el request a entidad

      //Creacion apoderado
      RegistroEstudianteRequest apo = nuevoRegistro.apoderado();

      Apoderado apoderado = new Apoderado();
      apoderado.setUsu_email(apo.email());
      apoderado.setUsu_pass(apo.password());
      apoderado.setUsu_numrun(apo.numRun());
      apoderado.setUsu_dvrun(apo.dvRun());
      apoderado.setUsu_dir(apo.direccion());
      apoderado.setUsu_nombre(apo.nombre());
      apoderado.setUsu_snombre(apo.segundoNombre());
      apoderado.setUsu_appaterno(apo.apellidoPaterno());
      apoderado.setUsu_apmaterno(apo.apellidoMaterno());

      apoderadoRespository.save(apoderado);

      // Creacion alumno
      RegistroEstudianteRequest alu = nuevoRegistro.alumno();

      Estudiante estudiante = new Estudiante();
      estudiante.setUsu_email(alu.email());
      estudiante.setUsu_pass(alu.password());
      estudiante.setUsu_numrun(alu.numRun());
      estudiante.setUsu_dvrun(alu.dvRun());
      estudiante.setUsu_dir(alu.direccion());
      estudiante.setUsu_nombre(alu.nombre());
      estudiante.setUsu_snombre(alu.segundoNombre());
      estudiante.setUsu_appaterno(alu.apellidoPaterno());
      estudiante.setUsu_apmaterno(alu.apellidoMaterno());

      //Join de tablas
      estudiante.setApoderado(apoderado);

      estudianteRepository.save(estudiante);

   }

}
