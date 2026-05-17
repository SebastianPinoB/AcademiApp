package com.example.AcademiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.BitacoraAsignatura;
import com.example.AcademiApp.model.request.BitacoraRequest;
import com.example.AcademiApp.service.BitacoraAsignaturaService;

@RestController
@RequestMapping("bitacora")
public class BitacoraAsignaturaController {

   @Autowired
   private BitacoraAsignaturaService bitacoraAsignaturaService;

   @PostMapping
    public ResponseEntity<?> guardar(@RequestBody BitacoraRequest request) {
        try {
            BitacoraAsignatura nuevaBitacora = bitacoraAsignaturaService.crearBitacora(request);
            return new ResponseEntity<>(nuevaBitacora, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
   
}
