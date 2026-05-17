package com.example.AcademiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.model.entities.Asignatura;
import com.example.AcademiApp.model.entities.BitacoraAsignatura;
import com.example.AcademiApp.model.request.BitacoraRequest;
import com.example.AcademiApp.repository.AsignaturaRepository;
import com.example.AcademiApp.repository.BitacoraAsignaturaRepository;

import jakarta.transaction.Transactional;

@Service
public class BitacoraAsignaturaService {

   @Autowired
   private BitacoraAsignaturaRepository bitacoraAsignaturaRepository;

   @Autowired
   private AsignaturaRepository asignaturaRepository;

   @Transactional
   public BitacoraAsignatura crearBitacora(BitacoraRequest request) {
      // Validar asignatura
      Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
            .orElseThrow(() -> new IllegalArgumentException(
                  "No se puede registrar la bitácora: Asignatura no encontrada con ID: " + request.getAsignaturaId()));

      BitacoraAsignatura bitacora = new BitacoraAsignatura();
      bitacora.setBitNombre(request.getBitNombre());
      bitacora.setBitFechaRegistro(request.getBitFechaRegistro());
      bitacora.setBitFechaRealClase(request.getBitFechaRealClase());
      bitacora.setBitObjetivoAprendizaje(request.getBitObjAprend());
      bitacora.setBitTemaTratadoClase(request.getBitTemasTratadoClase());
      bitacora.setBitHoraInicio(request.getBitHoraIni());
      bitacora.setBitHoraFin(request.getBitHoraFin());
      bitacora.setAsignatura(asignatura); // Asociamos la asignatura

      return bitacoraAsignaturaRepository.save(bitacora);
   }

}
