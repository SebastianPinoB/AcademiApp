package com.example.AcademiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.Evaluacion;
import com.example.AcademiApp.model.request.EvaluacionRequest;
import com.example.AcademiApp.service.EvaluacionService;

@RestController
@RequestMapping("/evaluacion")
public class EvaluacionController {

   @Autowired
   private EvaluacionService evaluacionService;

   @PostMapping
   public ResponseEntity<?> guardar(@RequestBody EvaluacionRequest request) {
      try {
         Evaluacion nuevaEvaluacion = evaluacionService.crearEvaluacion(request);
         return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
   }
}
