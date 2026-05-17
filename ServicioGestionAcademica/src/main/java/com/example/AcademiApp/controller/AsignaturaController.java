package com.example.AcademiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.Asignatura;
import com.example.AcademiApp.model.request.AsignaturaRequest;
import com.example.AcademiApp.service.AsignaturaService;

@RestController
@RequestMapping("asignaturas")
public class AsignaturaController {

   @Autowired
   private AsignaturaService asignaturaService;

   @PostMapping
   public ResponseEntity<?> guardar(@RequestBody AsignaturaRequest request) {
      try {
         Asignatura nuevaAsignatura = asignaturaService.crearAsignatura(request);
         return new ResponseEntity<>(nuevaAsignatura, HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
         // Si el nombre está duplicado, capturamos el error y respondemos un 400 con el
         // mensaje
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }

   @GetMapping
   public ResponseEntity<List<Asignatura>> listarTodas() {
      List<Asignatura> lista = asignaturaService.obtenerTodas();
      return ResponseEntity.ok(lista);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Asignatura> buscarPorId(@PathVariable int id) {
      Asignatura asignatura = asignaturaService.obtenerPorId(id);
      return ResponseEntity.ok(asignatura);
   }

}
