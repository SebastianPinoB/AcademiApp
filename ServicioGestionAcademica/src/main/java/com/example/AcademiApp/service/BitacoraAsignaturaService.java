package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AcademiApp.model.entities.Asignatura;
import com.example.AcademiApp.model.entities.BitacoraAsignatura;
import com.example.AcademiApp.model.request.BitacoraRequest;
import com.example.AcademiApp.repository.AsignaturaRepository;
import com.example.AcademiApp.repository.BitacoraAsignaturaRepository;


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

   @Transactional(readOnly = true)
   public List<BitacoraAsignatura> obtenerTodas() {
      return bitacoraAsignaturaRepository.findAll();
   }

   @Transactional(readOnly = true)
   public BitacoraAsignatura obtenerPorId(int id) {
      return bitacoraAsignaturaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Bitácora no encontrada con ID: " + id));
   }

   @Transactional
   public BitacoraAsignatura actualizarBitacora(int id, BitacoraRequest request) {
      BitacoraAsignatura bitacoraExistente = bitacoraAsignaturaRepository.findById(id)
            .orElseThrow(
                  () -> new IllegalArgumentException("No se puede actualizar: Bitácora no encontrada con ID: " + id));

      Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
            .orElseThrow(
                  () -> new IllegalArgumentException("Asignatura no encontrada con ID: " + request.getAsignaturaId()));

      // Actualizamos los campos de la bitácora
      bitacoraExistente.setBitNombre(request.getBitNombre());
      bitacoraExistente.setBitFechaRegistro(request.getBitFechaRegistro());
      bitacoraExistente.setBitFechaRealClase(request.getBitFechaRealClase());
      bitacoraExistente.setBitObjetivoAprendizaje(request.getBitObjAprend());
      bitacoraExistente.setBitTemaTratadoClase(request.getBitTemasTratadoClase());
      bitacoraExistente.setBitHoraInicio(request.getBitHoraIni());
      bitacoraExistente.setBitHoraFin(request.getBitHoraFin());
      bitacoraExistente.setAsignatura(asignatura);

      return bitacoraAsignaturaRepository.save(bitacoraExistente);
   }

   @Transactional
   public void eliminarBitacora(int id) {
      if (!bitacoraAsignaturaRepository.existsById(id)) {
         throw new IllegalArgumentException("No se puede eliminar: Bitácora no encontrada con ID: " + id);
      }
      bitacoraAsignaturaRepository.deleteById(id);
   }

}
